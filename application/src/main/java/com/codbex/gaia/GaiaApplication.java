/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.gaia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Gaia edition's entry point.
 *
 * <p>
 * Two of the scanned packages sit outside the platform's usual {@code components} / {@code engine}
 * roots, and both are named deliberately. {@code org.eclipse.dirigible.commons} carries a single
 * bean, {@code ConsoleWebsocketConfig}, which publishes the platform logging appender's stream at
 * {@code /websockets/ide/console} - the live source behind the Monitoring shell's Logs page.
 * {@code org.eclipse.dirigible.database} carries two, {@code HanaConnectionEnhancer} (propagates
 * the end user as {@code APPLICATIONUSER} on every HANA connection) and
 * {@code HanaDatabaseConfigurator} (the HANA connection-validation query and keepalive) - this
 * edition ships the HANA dialect, so both have to be reachable.
 *
 * <p>
 * All three are contributor-shaped - a {@code WebSocketConfigurer} and two {@code List<T>} intakes
 * - so leaving them out of scan reach fails silently rather than loudly. Upstream never has to name
 * them because its application class sits in {@code org.eclipse.dirigible} and scans the whole
 * tree; an edition that names its packages has to name these too. See
 * <a href="https://github.com/eclipse-dirigible/dirigible/issues/6635">dirigible#6635</a>.
 */
@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"com.codbex.gaia", "org.eclipse.dirigible.components", "org.eclipse.dirigible.engine",
        "org.eclipse.dirigible.commons", "org.eclipse.dirigible.database"})
@EnableScheduling
public class GaiaApplication {

    private static long startedAt;

    public static long getStartedAt() {
        return startedAt;
    }

    public static void main(String[] args) {
        startedAt = System.currentTimeMillis();
        System.out.println("------- Application is starting -------");
        SpringApplication.run(GaiaApplication.class, args);
    }

}
