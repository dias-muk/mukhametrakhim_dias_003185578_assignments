/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 * Dias Mukhametrakhim, NUID 003185578
 */
package UserInterface.Main;

import TheBusiness.Business.Business;
import TheBusiness.DataGenerator;

/**
 * Supplies the main frame with the business it displays. The data itself is
 * built by {@link DataGenerator}, so the user interface has only one place to
 * ask for it.
 */
class ConfigureABusiness {

    static Business initialize() {
        return DataGenerator.generateBusiness();
    }
}