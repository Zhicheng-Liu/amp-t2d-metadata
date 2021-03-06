/*
 *
 * Copyright 2019 EMBL - European Bioinformatics Institute
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

package uk.ac.ebi.ampt2d.metadata.importer.extractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.ampt2d.metadata.persistence.entities.File;
import uk.ac.ebi.ampt2d.metadata.importer.SraRetrieverByAccession;
import uk.ac.ebi.ampt2d.metadata.importer.converter.FileConverter;
import uk.ac.ebi.ampt2d.metadata.importer.xml.SraAnalysisXmlParser;
import uk.ac.ebi.ampt2d.metadata.importer.xml.SraXmlParser;
import uk.ac.ebi.ena.sra.xml.AnalysisType;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileExtractorFromAnalysisTest {

    private static final String ANALYSIS_ACCESSION = "ERZ496533";

    private static final String ANALYSIS_DOCUMENT_API_XML = "AnalysisDocumentApi.xml";

    private static final String ANALYSIS_DOCUMENT_DATABASE_XML = "AnalysisDocumentDatabase.xml";

    private static final String ANALYSIS_DOCUMENT_NOT_FOUND_XML = "AnalysisDocumentNotFound.xml";

    @Mock
    private SraRetrieverByAccession sraRetrieverByAccession;

    private SraXmlParser<AnalysisType> sraXmlParser = new SraAnalysisXmlParser();

    private FileConverter fileConverter = new FileConverter();

    @InjectMocks
    private FileExtractorFromAnalysis fileExtractorFromAnalysis = new FileExtractorFromAnalysis
            (sraRetrieverByAccession, sraXmlParser, fileConverter);

    @Test
    public void testFileExtractorFromAnalysisApi() throws Exception {
        when(sraRetrieverByAccession.getXml(ANALYSIS_ACCESSION)).thenReturn(new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(ANALYSIS_DOCUMENT_API_XML).toURI()))));
        List<File> files = fileExtractorFromAnalysis.extractFilesFromAnalysis(ANALYSIS_ACCESSION);
        assertEquals(2, files.size());
    }

    @Test
    public void testFileExtractorFromAnalysisDatabase() throws Exception {
        when(sraRetrieverByAccession.getXml(ANALYSIS_ACCESSION)).thenReturn(new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(ANALYSIS_DOCUMENT_DATABASE_XML).toURI()))));
        List<File> files = fileExtractorFromAnalysis.extractFilesFromAnalysis(ANALYSIS_ACCESSION);
        assertEquals(2, files.size());
    }

    @Test
    public void testFileExtractorFromInvalidAnalysis() throws Exception {
        when(sraRetrieverByAccession.getXml("INVALID_ACCESSION")).thenReturn(new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader().getResource(ANALYSIS_DOCUMENT_NOT_FOUND_XML).toURI()))));
        List<File> files = fileExtractorFromAnalysis.extractFilesFromAnalysis(ANALYSIS_ACCESSION);
        assertEquals(0, files.size());
    }

}