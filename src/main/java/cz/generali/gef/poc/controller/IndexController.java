package cz.generali.gef.poc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.generali.gef.poc.domain.Partner;
import cz.generali.gef.poc.repository.PartnerRepository;
import cz.generali.gef.poc.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ivan Dolezal(T911552) on 3.1.2015.
 *
 * @Author Ivan Dolezal
 */
@Controller
public class IndexController {

	private static final String VIEW_PAGE = "view";
	private static final String MODEL_VIEW = "model";

	@Autowired
	private PartnerService partnerService;

	@Autowired
	private PartnerRepository partnerRepository;

	@RequestMapping(value = { "/new" })
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "New partner");
		mav.addObject("viewName", "partner");
		mav.addObject("modelUrl", "model?id=");
		mav.setViewName(VIEW_PAGE);
		return mav;
	}

	@RequestMapping(value = { "/{id}" })
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("viewName", "partner");
		mav.addObject("modelUrl", "model?id=" + id);
		mav.setViewName(VIEW_PAGE);
		return mav;
	}

	@RequestMapping(value = { "view/{id}" })
	public ModelAndView view(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("viewName", "partner");
		mav.addObject("modelUrl", "model?wid=" + id);
		mav.setViewName(VIEW_PAGE);
		return mav;
	}

	@RequestMapping("/listModel")
	public ModelAndView listModel() throws JsonProcessingException {
		ModelAndView mav = new ModelAndView();

		mav.addObject("model", partnerService.getPartnerListModel());
		mav.setViewName(MODEL_VIEW);
		return mav;
	}

	@RequestMapping("/model")
	public ModelAndView getModel(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "wid", required = false) Long wid) throws JsonProcessingException {
		ModelAndView mav = new ModelAndView();

		if (id == null) {
			mav.addObject("model", partnerService.getNewPartnerModel());
		} else {
			mav.addObject("model", partnerService.getPartner(id, true));
		}
		if (wid != null) {
			mav.addObject("model", partnerService.getPartner(wid, false));
		}
		mav.setViewName(MODEL_VIEW);
		return mav;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String getModel(final Partner partner) {

		partnerRepository.save(partner);
		System.out.println(partner.getId());
		return "success";
	}

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String redirectToList() {
		return "redirect:/list";
	}

	@RequestMapping(value = { "/list", "/" })
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "Partners list");
		mav.addObject("viewName", "list");
		mav.addObject("modelUrl", "listModel");
		mav.setViewName(VIEW_PAGE);
		return mav;
	}
}
