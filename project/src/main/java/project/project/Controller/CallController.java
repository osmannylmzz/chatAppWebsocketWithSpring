//package project.project.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import project.project.model.Call;
//import project.project.service.CallService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/calls")
//public class CallController {
//
//    @Autowired
//    private CallService callService;
//
//    @PostMapping("/start")
//    public Call startCall(@RequestParam String callerUsername, @RequestParam String receiverUsername) {
//        return callService.startCall(callerUsername, receiverUsername);
//    }
//
//    @PostMapping("/end")
//    public Call endCall(@RequestParam String callId) {
//        return callService.endCall(callId);
//    }
//
//    @GetMapping("/history")
//    public List<Call> getAllCalls() {
//        return callService.getAllCalls();
//    }
//
//    @PostMapping("/mute")
//    public Call muteCall(@RequestParam String callId) {
//        return callService.muteCall(callId);
//    }
//
//    @PostMapping("/unmute")
//    public Call unmuteCall(@RequestParam String callId) {
//        return callService.unmuteCall(callId);
//    }
//
//    @PostMapping("/hold")
//    public Call holdCall(@RequestParam String callId) {
//        return callService.holdCall(callId);
//    }
//
//    @PostMapping("/resume")
//    public Call resumeCall(@RequestParam String callId) {
//        return callService.resumeCall(callId);
//    }
//}

