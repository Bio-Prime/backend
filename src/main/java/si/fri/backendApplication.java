package si.fri;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class backendApplication extends Application<backendConfiguration> {

    public static void main(final String[] args) throws Exception {
        new backendApplication().run(args);
    }

    @Override
    public String getName() {
        return "backend";
    }

    @Override
    public void initialize(final Bootstrap<backendConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final backendConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
