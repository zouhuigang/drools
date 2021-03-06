/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.pmml.regression.tests;

import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.pmml.PMML4Result;
import org.kie.api.pmml.PMMLRequestData;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.pmml.evaluator.api.executor.PMMLRuntime;
import org.kie.pmml.evaluator.core.PMMLContextImpl;
import org.kie.pmml.evaluator.core.utils.PMMLRequestDataBuilder;

public abstract class AbstractPMMLRegressionTest {

    private static KieContainer kieContainer;

    static {
        final KieServices kieServices = KieServices.get();
        kieContainer = kieServices.newKieClasspathContainer();
    }

    protected static PMMLRuntime getPMMLRuntime(String kbaseName) {
        KieBase kieBase = kieContainer.getKieBase(kbaseName);
        final KieRuntimeFactory kieRuntimeFactory = KieRuntimeFactory.of(kieBase);
        return kieRuntimeFactory.get(PMMLRuntime.class);
    }

    protected static PMMLRequestData getPMMLRequestData(String modelName, Map<String, Object> parameters) {
        String correlationId = "CORRELATION_ID";
        PMMLRequestDataBuilder pmmlRequestDataBuilder = new PMMLRequestDataBuilder(correlationId, modelName);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            Object pValue = entry.getValue();
            Class class1 = pValue.getClass();
            pmmlRequestDataBuilder.addParameter(entry.getKey(), pValue, class1);
        }
        return pmmlRequestDataBuilder.build();
    }

    protected PMML4Result evaluate(PMMLRuntime pmmlRuntime, final Map<String, Object> inputData, String modelName) {
        final PMMLRequestData pmmlRequestData = getPMMLRequestData(modelName, inputData);
        return pmmlRuntime.evaluate(modelName, new PMMLContextImpl(pmmlRequestData));
    }
}
