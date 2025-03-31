package com.codbex.gaia.integration.tests;

import org.eclipse.dirigible.integration.tests.api.SecurityIT;
import org.eclipse.dirigible.integration.tests.api.java.messaging.MessagingFacadeIT;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({//
        MessagingFacadeIT.class, //
        SecurityIT.class, //
})
public class DirigibileCommonTestSuiteIT {
}
