package project.project.Controller;

import project.project.Service.GroupsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.model.Groups;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

   private final GroupsService groupsService;

    @Autowired
    public GroupController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    //    @GetMapping
//    public List<Groups> getAllGroups() {
//
//        return groupsService.getAllGroups();
//    }
    @GetMapping("/{username}")
    public List<Groups> getGroupsByUsername(@PathVariable String username) {
        List<Groups> allGroups = groupsService.getAllGroups();
        return allGroups.stream()
                .filter(group -> group.getKullanıcılar().contains(username)) // Kullanıcı adı kontrolü
                .collect(Collectors.toList());
    }

    @PostMapping("/new")
    public ResponseEntity<Groups> createNewGroup(@RequestBody Groups group) {
        System.out.println("Gelen Veri: " + group);
        groupsService.saveGroups(group);
        return ResponseEntity.ok(group);
    }
}