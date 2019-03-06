package descriptor.command;

import java.util.ArrayList;
import java.util.List;

public class DocumentTypeCmd {
    private String name;
    private List<DescriptorCmd> descriptors = new ArrayList<>();

    public String getName() {
        return name;
    }

    public List<DescriptorCmd> getDescriptors() {
        return descriptors;
    }

    @Override
    public String toString() {
        return "DocumentTypeCmd{" +
                "name='" + name + '\'' +
                ", descriptors=" + descriptors +
                '}';
    }
}
