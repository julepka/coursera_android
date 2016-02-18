package com.example.dailyphoto.oath;

import java.io.File;
import java.util.Arrays;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class OAuth2SecurityConfiguration {

	@Configuration
	@EnableWebSecurity
	protected static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private UserDetailsService userDetailsService;
		
		@Autowired
		protected void registerAuthentication(
				final AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService);
		}
	}
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServer extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {
			
			http.csrf().disable();
			
			http.authorizeRequests()
				.antMatchers("/oauth/token").anonymous();
			
			http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/**")
				.access("#oauth2.hasScope('read')");
			
			http.authorizeRequests()
				.antMatchers("/**")
				.access("#oauth2.hasScope('write')");
		}

	}

	@Configuration
	@EnableAuthorizationServer
	@Order(Ordered.LOWEST_PRECEDENCE - 100)
	protected static class OAuth2Config extends
			AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		private ClientAndUserDetailsService combinedService_;

		public OAuth2Config() throws Exception {
			
			ClientDetailsService csvc = new InMemoryClientDetailsServiceBuilder()
					.withClient("mobile").authorizedGrantTypes("password")
					.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
					.scopes("read","write").resourceIds("photo")
					.and()
					.withClient("mobileReader").authorizedGrantTypes("password")
					.authorities("ROLE_CLIENT")
					.scopes("read").resourceIds("photo")
					.accessTokenValiditySeconds(3600).and().build();
			
			UserDetailsService svc = new InMemoryUserDetailsManager(
					Arrays.asList(
							User.create("admin", "pass", "ADMIN", "USER"),
							User.create("user0", "pass", "USER"),
							User.create("user1", "pass", "USER"),
							User.create("user2", "pass", "USER"),
							User.create("user3", "pass", "USER"),
							User.create("user4", "pass", "USER"),
							User.create("user5", "pass", "USER")));

			combinedService_ = new ClientAndUserDetailsService(csvc, svc);
		}

		@Bean
		public ClientDetailsService clientDetailsService() throws Exception {
			return combinedService_;
		}

		@Bean
		public UserDetailsService userDetailsService() {
			return combinedService_;
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.authenticationManager(authenticationManager);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients)
				throws Exception {
			clients.withClientDetails(clientDetailsService());
		}

	}
	
    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer(
            @Value("${keystore.file:src/main/resources/private/keystore}") String keystoreFile,
            @Value("${keystore.pass:changeit}") final String keystorePass) throws Exception {

        final String absoluteKeystoreFile = new File(keystoreFile).getAbsolutePath();

        return new EmbeddedServletContainerCustomizer () {

			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
		            TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
		            tomcat.addConnectorCustomizers(
		                    new TomcatConnectorCustomizer() {
								@Override
								public void customize(Connector connector) {
									connector.setPort(8443);
			                        connector.setSecure(true);
			                        connector.setScheme("https");

			                        Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
			                        proto.setSSLEnabled(true);
			                        proto.setKeystoreFile(absoluteKeystoreFile);
			                        proto.setKeystorePass(keystorePass);
			                        proto.setKeystoreType("JKS");
			                        proto.setKeyAlias("tomcat");
								}
		                    });
		    
			}
        };
    }
	

}
