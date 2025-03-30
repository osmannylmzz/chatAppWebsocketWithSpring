package project.project.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.project.Service.GroupsMessageService;
import project.project.model.GroupsMessage;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupsMessageController {

    private project.project.Service.GroupsMessageService groupsMessageService;


    @GetMapping("/page")
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("denemegrup");
    }
    @Autowired
    public GroupsMessageController(GroupsMessageService groupsMessageService) {
        this.groupsMessageService = groupsMessageService;
    }

    @GetMapping("/creta")
    public ModelAndView showCreatrForm() {
        return new ModelAndView("createGroup");
    }

    @GetMapping("/{groupname}/messages")
    public ResponseEntity<List<GroupsMessage>> getGroupMessages(@PathVariable String groupname) {

        List<GroupsMessage> groupMessages = groupsMessageService.getGroupsMessages(groupname);
        return ResponseEntity.ok(groupMessages);
    }

    @MessageMapping("/chat.sendGroupMessage/{groupname}")
    @SendTo("/topic/{groupname}")
    public GroupsMessage sendGroupMessage(@DestinationVariable("groupname") String groupName, @RequestBody GroupsMessage groupMessage) {
        // Gelen mesajın ait olduğu grup adı
        System.out.println("Group Name: " + groupName);

        // Mesajı kaydediyoruz
        groupsMessageService.saveMessage(groupMessage);

        // Mesajı geri döndürüp ilgili gruba gönderiyoruz
        return groupMessage;
    }

}
