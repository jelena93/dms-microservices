package process.controller;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import process.command.ActivityCmd;
import process.command.ProcessCmd;
import process.domain.Activity;
import process.domain.Process;
import process.dto.ActivityDto;
import process.dto.ProcessDto;
import process.dto.TreeDto;
import process.mapper.ActivityMapper;
import process.mapper.ProcessMapper;
import process.messaging.output.DocumentMessagingService;
import process.messaging.output.dto.DocumentMessagingOutputDto;
import process.service.ActivityService;
import process.service.ProcessService;

@RestController
@RequestMapping("/")
public class ProcessServiceController {

    private final ProcessService processService;
    private final ActivityService activityService;
    private final DocumentMessagingService documentMessagingService;
    private final ProcessMapper processMapper;
    private final ActivityMapper activityMapper;

    @Autowired
    public ProcessServiceController(ProcessService processService, ActivityService activityService,
                                    DocumentMessagingService documentMessagingService,
                                    ProcessMapper processMapper, ActivityMapper activityMapper) {
        this.processService = processService;
        this.activityService = activityService;
        this.documentMessagingService = documentMessagingService;
        this.processMapper = processMapper;
        this.activityMapper = activityMapper;
    }

    @GetMapping(path = "/all/{ownerId}")
    public List<TreeDto> getProcesses(@PathVariable long ownerId) throws Exception {
        List<Process> processes = processService.findByOwnerId(ownerId);
        List<TreeDto> data = new ArrayList<>();
        for (Process process : processes) {
            TreeDto p;
            String icon;
            icon = TreeDto.PROCESS_ICON;
            if (process.isPrimitive()) {
                icon = TreeDto.PROCESS_PRIMITIVE_ICON;
            }
            if (process.getParent() == null) {
                p = new TreeDto(process.getId(), "#", process.getName(), icon, process.isPrimitive());
            } else {
                p = new TreeDto(process.getId(), process.getParent().getId() + "", process.getName(), icon,
                        process.isPrimitive());
            }
            data.add(p);
            if (process.isPrimitive() && process.getActivityList() != null) {
                icon = TreeDto.ACTIVITY_ICON;
                for (Activity activity : process.getActivityList()) {
                    p = new TreeDto(activity.getId(), process.getId() + "", activity.getName(), icon);
                    data.add(p);
                }
            }
        }
        return data;
    }

    @GetMapping(path = "/process/{id}")
    public ProcessDto showProcess(@PathVariable long id) {
        Process process = processService.findOne(id);
        return processMapper.mapToModel(process);
    }

    @PostMapping(path = "/process")
    public ProcessDto addProcess(@RequestBody @Valid ProcessCmd processCmd) {
        System.out.println("addProcess " + processCmd);
        return processMapper.mapToModel(processService.save(processMapper.mapToEntity(processCmd)));
    }

    @PutMapping(path = "/process/{id}")
    public ProcessDto editProcess(@PathVariable Long id, @RequestBody @Valid ProcessCmd processCmd) throws Exception {
        Process process = processService.findOne(id);
        if (process == null) throw new Exception("There is no process with id " + id);
        List<Long> documentIds = new ArrayList<>();
        if (processCmd.isPrimitive() && !process.isPrimitive()) {
            deleteChildren(process, processService.findByParent(process), documentIds, true);
        } else if (!processCmd.isPrimitive() && process.isPrimitive()) {
            documentIds.addAll(process.getActivityList().stream().flatMap(a -> a.getInputList().stream())
                    .collect(Collectors.toList()));
            documentIds.addAll(process.getActivityList().stream().flatMap(a -> a.getOutputList().stream())
                    .collect(Collectors.toList()));
        }
        processMapper.updateEntityFromModel(processCmd, process);
        process = processService.update(processCmd, process);
        if (!documentIds.isEmpty()) {
            documentMessagingService.sendDeleteDocuments(new DocumentMessagingOutputDto(documentIds));
        }
        return processMapper.mapToModel(process);
    }

    @GetMapping(path = "/activity/{id}")
    public ActivityDto getActivity(@PathVariable long id) throws Exception {
        Activity activity = activityService.findOne(id);
        return activityMapper.mapToModel(activity);
    }

    @PostMapping(path = "/activity")
    public ActivityDto addActivity(@RequestBody @Valid ActivityCmd activityCmd) throws Exception {
        System.out.println("addActivity " + activityCmd);
        Process process = processService.findOne(activityCmd.getProcessId());
        if (process == null) throw new Exception("Process is required");

        Activity activity = activityMapper.mapToEntity(activityCmd);
        process.getActivityList().add(activity);
        processService.save(process);
        return activityMapper.mapToModel(activity);
    }

    @PutMapping(path = "/activity/{id}")
    public ActivityDto editActivity(@PathVariable Long id, @RequestBody @Valid ActivityCmd activityCmd) throws Exception {
        Activity activity = activityService.findOne(id);
        if (activity == null) {
            throw new Exception("There is no activity with id " + id);
        }

        activityMapper.updateEntityFromModel(activityCmd, activity);
        return activityMapper.mapToModel(activityService.save(activity));
    }

    private static void deleteChildren(Process process, List<Process> processes, List<Long> documentIds, boolean root) {
        List<Process> children = getChildren(process, processes);
        for (Process child : children) {
            deleteChildren(child, processes, documentIds, false);
        }
        if (!root) {
            documentIds.addAll(process.getActivityList().stream().flatMap(a -> a.getInputList().stream())
                    .collect(Collectors.toList()));
            documentIds.addAll(process.getActivityList().stream().flatMap(a -> a.getOutputList().stream())
                    .collect(Collectors.toList()));
        }
    }

    private static List<Process> getChildren(Process p, List<Process> lista) {
        List<Process> children = new ArrayList<>();
        for (Process process : lista) {
            if (p != null && p.equals(process.getParent())) {
                children.add(process);
            }
        }
        return children;
    }

}
