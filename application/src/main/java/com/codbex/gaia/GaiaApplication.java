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
 * The scan covers {@code org.eclipse.dirigible.commons} for a single bean,
 * {@code ConsoleWebsocketConfig}, which publishes the platform logging appender's stream at
 * {@code /websockets/ide/console} - the live source behind the Monitoring shell's Logs page.
 * Upstream picks it up because its application class sits in {@code org.eclipse.dirigible} and
 * scans the whole tree; this edition names its packages, so it has to name that one too.
 */
@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"com.codbex.gaia", "org.eclipse.dirigible.components", "org.eclipse.dirigible.engine",
        "org.eclipse.dirigible.commons"})
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
