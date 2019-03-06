package process.command;

public class ProcessCmd {
    private String name;
    private Long ownerId;
    private Long parentId;
    private boolean primitive;

    public String getName() {
        return name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getParentId() {
        return parentId;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    @Override
    public String toString() {
        return "ProcessCmd{" + "name='" + name + '\'' + ", ownerId='" + ownerId + '\'' + ", parentId=" + parentId
                + ", primitive=" + primitive + '}';
    }
}
