package helpers;

import org.aeonbits.owner.Config;

    @Config.Sources("classpath:config/ServerConfig.properties")
    public interface Credentials extends Config {

        @Key("server.host.userName")
        String userName();

        @Key("server.host.password")
        String password();

    }

