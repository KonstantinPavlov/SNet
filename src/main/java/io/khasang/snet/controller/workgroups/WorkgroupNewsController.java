package io.khasang.snet.controller.workgroups;


import io.khasang.snet.service.userauth.UserService;
import io.khasang.snet.service.workgroups.WorkgroupNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/news")
public class WorkgroupNewsController {

    @Autowired
    WorkgroupNewsService workgroupNewsService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getMessagesList(@PathVariable(value = "id") Long workGroupId) {
        return workgroupNewsService.getAllWorkgroupNewsList(workGroupId);
    }

}
