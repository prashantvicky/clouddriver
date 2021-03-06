/*
 * Copyright 2017 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.netflix.spinnaker.clouddriver.artifacts;

import com.netflix.spinnaker.clouddriver.artifacts.config.ArtifactCredentials;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ArtifactCredentialsRepository {
  private Map<String, List<ArtifactCredentials>> credentialsMap = new ConcurrentHashMap<>();

  public void save(ArtifactCredentials credentials) {
    String name = credentials.getName();
    List<ArtifactCredentials> stored = credentialsMap.getOrDefault(name, new ArrayList<>());
    stored.add(credentials);
    credentialsMap.put(credentials.getName(), stored);
  }

  public List<ArtifactCredentials> getAllCredentials() {
    return credentialsMap.values()
        .stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }
}
