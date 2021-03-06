package net.bzk.flow.api;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.bzk.flow.model.RunLog;
import net.bzk.flow.run.dao.RunLogDao;

@CrossOrigin(maxAge = 3600, methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.PATCH,
		RequestMethod.OPTIONS, RequestMethod.HEAD }, allowedHeaders = "*", origins = "*")
@Validated
@Controller
@RequestMapping(value = "run/log/")
public class RunLogController {
	@Inject
	private RunLogDao dao;

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "{uid}", method = RequestMethod.GET, params = "type=runflow")
	public List<RunLog> listByRunFlowUid(@PathVariable String uid) {
		return dao.findByRunFlowUidOrderByCreateAtAsc(uid);
	}

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "{uid}", method = RequestMethod.GET, params = "type=action")
	public List<RunLog> listByActionUid(@PathVariable String uid) {
		return dao.findByActionUidOrderByCreateAtAsc(uid);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public void deleteBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
		dao.deleteByCreateAtBefore(date);
	}

}
