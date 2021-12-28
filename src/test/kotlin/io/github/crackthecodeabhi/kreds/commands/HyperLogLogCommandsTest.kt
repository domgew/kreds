/*
 *  Copyright (C) 2021 Abhijith Shivaswamy
 *   See the notice.md file distributed with this work for additional
 *   information regarding copyright ownership.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package io.github.crackthecodeabhi.kreds.commands

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class HyperLogLogCommandsTest : FunSpec({
    lateinit var c: HyperLogLogCommands
    val clientSetup = ClientSetup().then { c = it.client }
    beforeSpec(clientSetup)
    afterSpec(ClientTearDown(clientSetup))

    test("pfadd").config(enabledOrReasonIf = clientSetup.enableIf(REDIS_6_0_0)) {
        c.pfadd("newkey", "element1", "element2") shouldBe 1
        c.pfadd("newkey1", "element1", "element2") shouldBe 1
    }
    test("pfcount").config(enabledOrReasonIf = clientSetup.enableIf(REDIS_6_0_0)) {
        c.pfcount("newkey", "newkey1") shouldBe 2
    }
    test("pfmerge").config(enabledOrReasonIf = clientSetup.enableIf(REDIS_6_0_0)) {
        c.pfmerge("newkey", "newkey1").shouldBeOk()
    }
})