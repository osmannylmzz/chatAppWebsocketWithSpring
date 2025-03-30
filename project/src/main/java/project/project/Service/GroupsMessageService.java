package project.project.Service;

import project.project.Repository.GroupMessageRepository;
import project.project.Repository.GroupMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.model.GroupsMessage;

import java.util.List;

@Service
public class GroupsMessageService {

    @Autowired

    private GroupMessageRepository groupMessageRepository;

    public GroupsMessage saveMessage (GroupsMessage groupsMessage) {
        return groupMessageRepository.save(groupsMessage);
    }
    public List<GroupsMessage> getGroupsMessages(String groupname) {
        return groupMessageRepository.findByGroupname(groupname);

    }
}
