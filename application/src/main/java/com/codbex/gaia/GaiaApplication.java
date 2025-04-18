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

@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"com.codbex.gaia", "org.eclipse.dirigible.components"})
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
