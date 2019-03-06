package document.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import document.command.DocumentCmd;
import document.domain.Document;
import document.dto.DocumentDto;
import document.elasticsearch.DocumentIndexer;
import document.elasticsearch.ElasticSearchMapper;
import document.elasticsearch.service.DocumentService;
import document.mapper.DocumentMapper;
import org.apache.commons.codec.binary.Base64;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.IndexNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DocumentServiceController {

    private final DocumentMapper documentMapper;
    private final DocumentIndexer documentIndexer;
    private final DocumentService documentService;

    @Autowired
    public DocumentServiceController(
            DocumentMapper documentMapper, DocumentIndexer documentIndexer, DocumentService documentService) {
        this.documentMapper = documentMapper;
        this.documentIndexer = documentIndexer;
        this.documentService = documentService;
    }

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam Long ownerId, @RequestParam(required = false) String query) throws Exception {
        Map<String, Object> map = new HashMap<>();
        try {
            SearchResponse searchResponse = documentService.searchDocumentsForOwner(ownerId, query);
            List<DocumentDto> dtos = ElasticSearchMapper.mapToDocumentList(searchResponse);
            map.put("documents", dtos);
            map.put("total", searchResponse.getHits().getTotalHits());
        } catch (IndexNotFoundException e) {
            System.out.println("search error" + e.getMessage());
        }
        return map;
    }

    @GetMapping("/{ownerId}/all")
    public List<DocumentDto> all(@PathVariable long ownerId) throws Exception {
        try {
            return ElasticSearchMapper.mapToDocumentList(documentService.findAll(ownerId));
        } catch (IndexNotFoundException e) {
            System.out.println("all " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @PostMapping
    public ResponseEntity<Long> addDocument(HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DocumentCmd documentCmd = mapper.readValue(request.getParameter("documentCmd"), DocumentCmd.class);
        System.out.println(documentCmd);

        Document document = documentMapper.mapToEntity(documentCmd);

        Part filePart = request.getPart("file");
        document.setFile(ByteStreams.toByteArray(filePart.getInputStream()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream input = filePart.getInputStream();
        int read = -1;
        while ((read = input.read()) != -1) {
            output.write(read);
        }
        input.close();
        String content = Base64.encodeBase64String(output.toByteArray());
        document.setContent(content);

        documentIndexer.save(document);
        System.out.println("sending " + document.getId());
        return new ResponseEntity<>(document.getId(), HttpStatus.OK);
    }

    @GetMapping("/download/{ownerId}/{documentId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable long ownerId,
                                               @PathVariable long documentId) throws Exception {
        SearchResponse searchResponse = documentService.findOne(ownerId, documentId);
        ObjectMapper mapper = new ObjectMapper();
        DocumentDto documentDto = new DocumentDto();
        if (searchResponse.getHits().getHits().length > 0) {
            documentDto = mapper.readValue(
                    searchResponse.getHits().getHits()[0].getSourceAsString(), DocumentDto.class);
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(documentDto.getAttachment().getContentType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + documentDto.getFileName());
        header.setContentLength(documentDto.getFile().length);
        return new ResponseEntity<>(documentDto.getFile(), header, HttpStatus.OK);
    }

    @GetMapping(path = "/{ownerId}/{documentId}")
    public ResponseEntity<byte[]> showFile(@PathVariable long ownerId, @PathVariable long documentId) throws Exception {
        SearchResponse searchResponse = documentService.findOne(ownerId, documentId);
        DocumentDto documentDto = new DocumentDto();
        ObjectMapper mapper = new ObjectMapper();
        if (searchResponse.getHits().getHits().length > 0) {
            documentDto = mapper.readValue(searchResponse.getHits().getHits()[0].getSourceAsString(), DocumentDto.class);
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(documentDto.getAttachment().getContentType()));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + documentDto.getFileName());
        header.setContentLength(documentDto.getFile().length);
        return new ResponseEntity<>(documentDto.getFile(), header, HttpStatus.OK);
    }

}
