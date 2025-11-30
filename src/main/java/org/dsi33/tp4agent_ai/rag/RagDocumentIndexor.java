package org.dsi33.tp4agent_ai.rag;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class RagDocumentIndexor {

    @Value("classpath:/pdfs/la-sante-publique.pdf")
    private Resource pdfResource;

    @Value("file.json")
    private String storeFileName;

    @Bean
    public SimpleVectorStore getVectorStore(EmbeddingModel embeddingModel) {

        SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();

        // Ensure folder exists
        Path path = Paths.get("src", "main", "resources", "store");
        File folder = path.toFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, storeFileName);

        if (!file.exists() || file.length() == 0) {

            System.out.println("➡ Vector store not found or empty — creating new one...");

            PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResource);
            List<Document> documents = reader.get();

            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> chunks = textSplitter.apply(documents);

            vectorStore.add(chunks);
            vectorStore.save(file);

        } else {
            System.out.println("➡ Loading existing vector store from file.json...");
            vectorStore.load(file);
        }

        return vectorStore;
    }
}
