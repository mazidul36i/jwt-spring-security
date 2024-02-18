package com.gliesestudio.jwt.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

/**
 * Web config to secure and redirect http requests to https
 * <p color="yellow">TODO: Not to be used before configuring spring security</p>
 *
 * @author MazidulIslam
 * @since 09-12-2023
 */
//@Configuration
public class WebConfig {

    /**
     * @return {@link ServletWebServerFactory}
     */
    //    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        // Enable SSL Traffic
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory() {

            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection securityCollection = new SecurityCollection();
                securityCollection.addPattern("/*");
                constraint.addCollection(securityCollection);
                context.addConstraint(constraint);
            }
        };

        // Add HTTP to HTTPS redirect
        tomcatServletWebServerFactory.addAdditionalTomcatConnectors(httpToHttpsConnector());
        return tomcatServletWebServerFactory;
    }

    /**
     * Http to https and port config
     *
     * @return {@link Connector} configured https connection
     */
    private Connector httpToHttpsConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8433);
        return connector;
    }

}