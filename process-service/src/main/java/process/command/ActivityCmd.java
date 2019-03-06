package process.command;

import java.util.ArrayList;
import java.util.List;

public class ActivityCmd {
    private String name;
    private Long processId;
    private List<Long> inputListDocumentTypes = new ArrayList<>();
    private List<Long> outputListDocumentTypes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Long getProcessId() {
        return processId;
    }

    public List<Long> getInputListDocumentTypes() {
        return inputListDocumentTypes;
    }

    public List<Long> getOutputListDocumentTypes() {
        return outputListDocumentTypes;
    }

    @Override
    public String toString() {
        return "ActivityCmd{" + "name='" + name + '\'' + ", processId=" + processId + ", inputListDocumentTypes="
                + inputListDocumentTypes + ", outputListDocumentTypes=" + outputListDocumentTypes + '}';
    }
}
