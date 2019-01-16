/*



 */
package com.igomall.controller.admin;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.igomall.FileType;
import com.igomall.Message;
import com.igomall.Setting;
import com.igomall.service.CacheService;
import com.igomall.service.FileService;
import com.igomall.util.SystemUtils;

/**
 * Controller - 系统设置
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@Controller("adminstingController")
@RequestMapping("/admin/setting")
public class SettingController extends BaseController {

	@Inject
	private FileService fileService;
	
	@Inject
	private CacheService cacheService;

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(ModelMap model) {
		model.addAttribute("setting", SystemUtils.getSetting());
		model.addAttribute("locales", Setting.Locale.values());
		model.addAttribute("watermarkPositions", Setting.WatermarkPosition.values());
		model.addAttribute("roundTypes", Setting.RoundType.values());
		model.addAttribute("registerTypes", Setting.RegisterType.values());
		model.addAttribute("captchaTypes", Setting.CaptchaType.values());
		model.addAttribute("stockAllocationTimes", Setting.StockAllocationTime.values());
		return "admin/setting/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public String update(Setting setting, MultipartFile watermarkImageFile, RedirectAttributes redirectAttributes) {
		if (!isValid(setting)) {
			return ERROR_VIEW;
		}
		if (setting.getDefaultPointScale() > setting.getMaxPointScale()) {
			return ERROR_VIEW;
		}
		Setting srcSetting = SystemUtils.getSetting();
		if (StringUtils.isEmpty(setting.getSmtpPassword())) {
			setting.setSmtpPassword(srcSetting.getSmtpPassword());
		}
		if (watermarkImageFile != null && !watermarkImageFile.isEmpty()) {
			if (!fileService.isValid(FileType.image, watermarkImageFile)) {
				addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
				return "redirect:edit";
			}
			String watermarkImage = fileService.uploadLocal(FileType.image, watermarkImageFile);
			setting.setWatermarkImage(watermarkImage);
		} else {
			setting.setWatermarkImage(srcSetting.getWatermarkImage());
		}
		if (StringUtils.isEmpty(setting.getSmsSn()) || StringUtils.isEmpty(setting.getSmsKey())) {
			setting.setSmsSn(null);
			setting.setSmsKey(null);
		}
		setting.setIsCnzzEnabled(srcSetting.getIsCnzzEnabled());
		setting.setCnzzSiteId(srcSetting.getCnzzSiteId());
		setting.setCnzzPassword(srcSetting.getCnzzPassword());

		SystemUtils.setSetting(setting);
		cacheService.clear();

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:edit";
	}

}