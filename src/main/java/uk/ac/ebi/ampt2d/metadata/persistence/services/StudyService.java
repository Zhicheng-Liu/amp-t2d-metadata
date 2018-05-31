/*
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package uk.ac.ebi.ampt2d.metadata.persistence.services;

import com.querydsl.core.types.Predicate;
import uk.ac.ebi.ampt2d.metadata.persistence.entities.Study;

import java.util.List;

public interface StudyService {

    Iterable<Study> findAll(Predicate predicate);

    List<Study> findStudiesByTaxonomyId(long id);

    List<Study> findStudiesByTaxonomyName(String name);

}
