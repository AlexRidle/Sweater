<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
Login page
<@l.login "/login" />
<a href="/registration">Not registered?</a>
</@c.page>