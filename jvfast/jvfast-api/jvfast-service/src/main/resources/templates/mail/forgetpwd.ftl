<style>
    .mmsgLetter {
        width: 580px;
        margin: 0 auto;
        padding: 10px;
        color: #333;
        background: #fff;
        border: 0px solid #aaa;
        border: 1px solid #aaa \9;
        border-radius: 5px;
        -webkit-box-shadow: 3px 3px 10px #999;
        -moz-box-shadow: 3px 3px 10px #999;
        box-shadow: 3px 3px 10px #999;
        font-family: Verdana, sans-serif;
    }

    .mmsgLetter a:link,
    .mmsgLetter a:visited {
        color: #407700;
    }

    .mmsgLetterContent {
        text-align: left;
        padding: 30px;
        font-size: 14px;
        line-height: 1.5;
        background: url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_bg_wmark.jpg) no-repeat top right;
    }

    .mmsgLetterContent h3 {
        color: #000;
        font-size: 20px;
        font-weight: bold;
        margin: 20px 0 20px;
        border-top: 2px solid #eee;
        padding: 20px 0 0 0;
        font-family: "微软雅黑", "黑体", "Lucida Grande", Verdana, sans-serif;
    }

    .mmsgLetterContent p {
        margin: 20px 0;
        padding: 0;
    }

    .mmsgLetterContent .salutation {
        font-weight: bold;
    }

    .mmsgLetterContent .mmsgMoreInfo {
    }

    .mmsgLetterContent a.mmsgButton {
        display: block;
        float: left;
        height: 40px;
        text-decoration: none;
        text-align: center;
        cursor: pointer;
    }

    .mmsgLetterContent a.mmsgButton span {
        display: block;
        float: left;
        padding: 0 25px;
        height: 40px;
        line-height: 36px;
        font-size: 14px;
        font-weight: bold;
        color: #fff;
        text-shadow: 1px 0 0 #235e00;
    }

    .mmsgLetterContent a.mmsgButton:link,
    .mmsgLetterContent a.mmsgButton:visited {
        background: #338702 url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_btn.png) no-repeat right -40px;
    }

    .mmsgLetterContent a.mmsgButton:link span,
    .mmsgLetterContent a.mmsgButton:visited span {
        background: url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_btn.png) no-repeat 0 0;
    }

    .mmsgLetterContent a.mmsgButton:hover,
    .mmsgLetterContent a.mmsgButton:active {
        background: #338702 url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_btn.png) no-repeat right -120px;
    }

    .mmsgLetterContent a.mmsgButton:hover span,
    .mmsgLetterContent a.mmsgButton:active span {
        background: url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_btn.png) no-repeat 0 -80px;
    }

    .mmsgLetterInscribe {
        padding: 40px 0 0;
    }

    .mmsgLetterInscribe .mmsgAvatar {
        float: left;
    }

    .mmsgLetterInscribe .mmsgName {
        margin: 0 0 10px;
    }

    .mmsgLetterInscribe .mmsgSender {
        margin: 0 0 0 54px;
    }

    .mmsgLetterInscribe .mmsgInfo {
        font-size: 12px;
        margin: 0;
        line-height: 1.2;
    }

    .mmsgLetterHeader {
        height: 23px;
        background: url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_bg_topline.png) repeat-x 0 0;
    }

    .mmsgLetterFooter {
        margin: 16px;
        text-align: center;
        font-size: 12px;
        color: #888;
        text-shadow: 1px 0px 0px #eee;
    }

    .mmsgLetterClr {
        clear: both;
        overflow: hidden;
        height: 1px;
    }


    .mmsgLetterUser {
        padding: 10px 0;
    }

    .mmsgLetterUserItem {
        padding: 0 0 20px 0;
    }

    .mmsgLetterUserAvatar {
        height: 40px;
        border: 1px solid #ccc;
        padding: 2px;
        display: block;
        float: left;
    }

    .mmsgLetterUserAvatar img {
        width: 40px;
        height: 40px;
    }

    .mmsgLetterInfo {
        margin-left: 48px;
    }

    .mmsgLetterName {
        display: block;
        color: #5fa207;
        font-weight: bold;
        margin-left: 10px;
    }

    .mmsgLetterDesc {
        font-size: 12px;
        float: left;
        height: 43px;
        background: url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_chat_right.gif) no-repeat right top;
    }

    .mmsgLetterDesc div {
        white-space: nowrap;
        float: left;
        height: 43px;
        padding: 0 20px;
        line-height: 40px;
        background: url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_chat_left.gif) no-repeat left top;
    }

    .mmsgLetterUser {
    }

    .mmsgLetterAvatar {
        float: left;
    }

    .mmsgLetterInfo {
        margin: 0 0 0 60px;
    }

    .mmsgLetterNickName {
        font-size: 14px;
        font-weight: bold;
    }

    .mmsgLetterUin {
        font-size: 12px;
        color: #666;
    }

    .mmsgLetterUser {
        padding: 10px 0;
    }

    .mmsgLetterUserItem {
        padding: 0 0 20px 0;
    }

    .mmsgLetterUserAvatar {
        height: 40px;
        border: 1px solid #ccc;
        padding: 2px;
        display: block;
        float: left;
    }

    .mmsgLetterUserAvatar img {
        width: 40px;
        height: 40px;
    }

    .mmsgLetterInfo {
        margin-left: 48px;
    }

    .mmsgLetterName {
        display: block;
        color: #5fa207;
        font-weight: bold;
        margin-left: 10px;
        padding-top: 10px;
    }

    .mmsgLetterDesc {
        font-size: 12px;
        float: left;
        height: 43px;
        background: url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_chat_right.gif) no-repeat right top;
    }

    .mmsgLetterDesc div {
        white-space: nowrap;
        float: left;
        height: 43px;
        padding: 0 20px;
        line-height: 40px;
        background: url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_chat_left.gif) no-repeat left top;
    }

</style>

<div style="background-color:#d0d0d0;background-image:url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_bg.png);text-align:center;padding:40px;">
    <div class="mmsgLetter"
         style="width:580px;margin:0 auto;padding:10px;color:#333;background-color:#fff;border:0px solid #aaa;border-radius:5px;-webkit-box-shadow:3px 3px 10px #999;-moz-box-shadow:3px 3px 10px #999;box-shadow:3px 3px 10px #999;font-family:Verdana, sans-serif; ">

        <div class="mmsgLetterHeader"
             style="height:23px;background:url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_bg_topline.png) repeat-x 0 0;">

        </div>
        <div class="mmsgLetterContent"
             style="text-align:left;padding:30px;font-size:14px;line-height:1.5;background:url(https://jvfast.pingbook.top/img/logo.png) no-repeat top right;">

            <div>

                <p>你好${name}!</p>
                <p>
                    忘记密码了吗？别着急，请点击以下链接，我们协助您找回密码：
                </p>
                <p style="word-wrap:break-word;word-break:break-all;">
                    <a href="${url}"
                       target="_blank">${url}</a>
                </p>

                <p>
                    为保障您的帐号安全，请在24小时内点击该链接，您也可以将链接复制到浏览器地址栏访问。如果您并未请求忘记密码操作，请忽略本邮件，由此给您带来的不便请谅解。
                </p>
                <p>
                    本邮件由系统自动发出，请勿直接回复！
                </p>
                <p style="float: right">
                    ${date}
                </p>

            </div>

            <div class="mmsgLetterInscribe" style="padding:40px 0 0;">
                <img class="mmsgAvatar"
                     src="http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_avatar_01.png"
                     style="float:left;"/>
                <div class="mmsgSender" style="margin:0 0 0 54px;">
                    <p class="mmsgName" style="margin:0 0 10px;">开发团队</p>
                    <p class="mmsgInfo" style="font-size:12px;margin:0;line-height:1.2;">
                        <#--                        开发团队<br/>-->
                        <a href="mailto:claire1023@qq.com" style="color:#407700;">contact@pingbook.top</a>
                    </p>
                </div>
            </div>
        </div>

        <div class="mmsgLetterFooter"
             style="margin:16px;text-align:center;font-size:12px;color:#888;text-shadow:1px 0px 0px #eee;">

            <img src="/cgi-bin/readtemplate?sid=$SID$&loc=pushmail1,weixiniphone,show,44"
                 style="width:1px;height:1px;"/>
            <img src="http://weixin.qq.com/cgi-bin/reportforpromote?uin=$RCPTUIN$&sdate=$SDATE$&tver=$TVER$"
                 style="width:1px;height:1px;"/>
        </div>
    </div>


</div>
