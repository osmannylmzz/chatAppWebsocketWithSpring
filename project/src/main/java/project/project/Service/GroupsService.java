package project.project.Service;

import project.project.Repository.GroupsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.model.Groups;

import java.util.List;

@Service
public class GroupsService {
    @Autowired
    private GroupsRepository groupsRepository;

    public Groups saveGroups(Groups groups) {
        return groupsRepository.save(groups);
    }
    public List<Groups> getAllGroups() {
        return groupsRepository.findAll();
    }
}
