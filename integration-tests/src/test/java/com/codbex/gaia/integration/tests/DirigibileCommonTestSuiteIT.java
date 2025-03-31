package com.codbex.gaia.integration.tests;

import org.eclipse.dirigible.integration.tests.api.SecurityIT;
import org.eclipse.dirigible.integration.tests.api.java.messaging.MessagingFacadeIT;
import org.eclipse.dirigible.integration.tests.api.javascript.cms.CmsSuiteIT;
import org.eclipse.dirigible.integration.tests.api.rest.ODataAPIIT;
import org.eclipse.dirigible.integration.tests.ui.tests.MailIT;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({//
        CmsSuiteIT.class, //
        MailIT.class, //
        MessagingFacadeIT.class, //
        ODataAPIIT.class, //
        SecurityIT.class, //
})
public class DirigibileCommonTestSuiteIT {
}
