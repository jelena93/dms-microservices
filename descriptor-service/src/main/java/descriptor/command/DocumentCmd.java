package descriptor.command;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

public class DocumentCmd {
    private boolean input;
    private Long ownerId;
    private Long activityId;
    private String fileName;
    private Long documentTypeId;
    private List<DescriptorCmd> descriptors = new ArrayList<>();

    @JsonIgnore
    private MultipartFile file;

    public boolean isInput() {
        return input;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public List<DescriptorCmd> getDescriptors() {
        return descriptors;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setInput(boolean input) {
        this.input = input;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public void setDescriptors(List<DescriptorCmd> descriptors) {
        this.descriptors = descriptors;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "DocumentCmd{" + "input=" + input + ", ownerId=" + ownerId + ", fileName='" + fileName + '\''
                + ", documentTypeId=" + documentTypeId + ", descriptors=" + descriptors + ", file=" + file + '}';
    }
}
