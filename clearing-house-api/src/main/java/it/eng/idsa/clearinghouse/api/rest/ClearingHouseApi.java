package it.eng.idsa.clearinghouse.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.eng.idsa.clearinghouse.api.service.ClearingHouseServiceManager;
import it.eng.idsa.clearinghouse.model.NotificationContent;
import it.eng.idsa.clearinghouse.model.json.JsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Api(value = "", tags = "Clearing House API")
public class ClearingHouseApi {

    @Autowired
    private ClearingHouseServiceManager clearingHouseServiceManager;

    @ApiOperation(value = "List all events under my context")
    @RequestMapping(value = "data/all", method = RequestMethod.GET)
    @ResponseBody
    public List<NotificationContent> get() throws Exception {
        return clearingHouseServiceManager.getClearingHouseService().retrieveAllNotifications();
    }

    @ApiOperation(value = "Create an event")
    @RequestMapping(value = "data", method = RequestMethod.POST)
    @ResponseBody
    public void create(@RequestBody String notification) throws Exception {
        clearingHouseServiceManager.getClearingHouseService().registerNotification((NotificationContent) JsonHandler.convertFromJson(notification,
                NotificationContent.class));
    }


    @ApiOperation(value = "Get a specific event under Id")
    @RequestMapping(value = "data", method = RequestMethod.GET)
    @ResponseBody
    public NotificationContent get(@RequestParam("Id") String id) throws Exception {
        return clearingHouseServiceManager.getClearingHouseService().retrieveNotification(id);
    }

    @ApiOperation(value = "Get specific events under Correlated Message Id")
    @RequestMapping(value = "data/correlated", method = RequestMethod.GET)
    @ResponseBody
    public List<NotificationContent> getRelated(@RequestParam("Id") String id) throws Exception {
        return clearingHouseServiceManager.getClearingHouseService().retrieveNotifications(id);
    }
}