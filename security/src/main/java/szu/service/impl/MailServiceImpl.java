package szu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import szu.common.api.CommonResult;
import szu.service.MailService;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service.impl
 * @Author: Origami
 * @Date: 2023/11/4 17:42
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Value("${spring.mail.username}")
    private String sendMailer;

    @Override
    public CommonResult<String> sendTextMailMessage(String to, String pin) {
        System.out.println("to = " + to);
        String subject = "Bilibili | 验证码";
        String s1 = "【Bilibili】 你正在进行【邮箱";

        String s = to.split("@")[1];
        int length = to.split("@")[0].length();
        String usernamePrefix = to.split("@")[0].substring(0, 2);
        String usernameSuffix = to.split("@")[0].substring(length - 1, length);
        int starLen = length - 3;
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < starLen; i++) {
            stars.append("*");
        }
        String s2 = "注册验证】，验证码";
        String s3 = "。提供给他人会导致自己的邮箱被占用和资产损失，若非本人操作，请修改邮箱";
        String text = "" + s1 + usernamePrefix + stars + usernameSuffix + "@" + s + s2 + pin + s3;
        System.out.println(text);
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            mimeMessageHelper.setFrom(sendMailer);
            mimeMessageHelper.setTo(to.split(","));
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text);
            mimeMessageHelper.setSentDate(new Date());
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            return CommonResult.success(null, "发送邮件成功！");
        } catch (MessagingException e) {
            e.printStackTrace();
            return CommonResult.failed("发送邮件失败");
        }
    }

}
