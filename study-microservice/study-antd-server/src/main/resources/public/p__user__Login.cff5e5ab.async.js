(self.webpackChunkant_design_pro=self.webpackChunkant_design_pro||[]).push([[9531],{5966:function(ie,j,e){"use strict";var A=e(22122),g=e(81253),x=e(67294),t=e(98757),O=["fieldProps","proFieldProps"],v=["fieldProps","proFieldProps"],$="text",Oe=function(l){var a=l.fieldProps,R=l.proFieldProps,K=(0,g.Z)(l,O);return x.createElement(t.Z,(0,A.Z)({mode:"edit",valueType:$,fieldProps:a,filedConfig:{valueType:$},proFieldProps:R},K))},ce=function(l){var a=l.fieldProps,R=l.proFieldProps,K=(0,g.Z)(l,v);return x.createElement(t.Z,(0,A.Z)({mode:"edit",valueType:"password",fieldProps:a,proFieldProps:R,filedConfig:{valueType:$}},K))},G=Oe;G.Password=ce,G.displayName="ProFormComponent",j.Z=G},5894:function(ie,j,e){"use strict";e.d(j,{A:function(){return X}});var A=e(9715),g=e(93766),x=e(22122),t=e(67294),O=e(49111),v=e(19650),$=e(96156),Oe=e(84305),ce=e(39559),G=e(28481),f=e(28991),l=e(8812),a=e(66758),R=e(96138),K=e(56725),P=e(53621),ue=e(94184),te=e.n(ue),de=t.forwardRef(function(N,L){var ve=t.useContext(a.Z),ae=ve.groupProps,h=(0,f.Z)((0,f.Z)({},ae),N),Te=h.children,M=h.collapsible,u=h.defaultCollapsed,C=h.style,c=h.labelLayout,E=h.title,b=E===void 0?N.label:E,F=h.tooltip,me=h.align,fe=me===void 0?"start":me,w=h.direction,k=h.size,Ne=k===void 0?32:k,Ae=h.titleStyle,pe=h.titleRender,ne=h.spaceProps,Re=h.extra,ge=h.autoFocus,Ue=(0,K.Z)(function(){return u||!1},{value:N.collapsed,onChange:N.onCollapse}),be=(0,G.Z)(Ue,2),Le=be[0],re=be[1],$e=(0,t.useContext)(ce.ZP.ConfigContext),se=$e.getPrefixCls,q=se("pro-form-group"),he=M&&t.createElement(l.Z,{style:{marginRight:8},rotate:Le?void 0:90}),Ie=t.createElement(P.Z,{label:he?t.createElement("div",null,he,b):b,tooltip:F}),De=pe?pe(Ie,N):Ie,Se=[],Ke=t.Children.toArray(Te).map(function(o,Be){var je;return t.isValidElement(o)&&(o==null||(je=o.props)===null||je===void 0?void 0:je.hidden)?(Se.push(o),null):Be===0&&t.isValidElement(o)&&ge?t.cloneElement(o,(0,f.Z)((0,f.Z)({},o.props),{},{autoFocus:ge})):o});return t.createElement("div",{className:te()(q,(0,$.Z)({},"".concat(q,"-twoLine"),c==="twoLine")),style:C,ref:L},Se.length>0&&t.createElement("div",{style:{display:"none"}},Se),(b||F||Re)&&t.createElement("div",{className:"".concat(q,"-title"),style:Ae,onClick:function(){re(!Le)}},Re?t.createElement("div",{style:{display:"flex",width:"100%",alignItems:"center",justifyContent:"space-between"}},De,t.createElement("span",{onClick:function(Be){return Be.stopPropagation()}},Re)):De),M&&Le?null:t.createElement(v.Z,(0,x.Z)({},ne,{className:"".concat(q,"-container"),size:Ne,align:fe,direction:w,style:(0,f.Z)({rowGap:0},ne==null?void 0:ne.style)}),Ke))});de.displayName="ProForm-Group";var Fe=de,H=e(7525),Q=e(88374);function X(N){return t.createElement(Q.I,(0,x.Z)({layout:"vertical",submitter:{render:function(ve,ae){return ae.reverse()}},contentRender:function(ve,ae){return t.createElement(t.Fragment,null,ve,ae)}},N))}X.Group=Fe,X.useForm=g.Z.useForm,X.Item=H.Z},53621:function(ie,j,e){"use strict";var A=e(22385),g=e(69713),x=e(96156),t=e(84305),O=e(39559),v=e(67294),$=e(68628),Oe=e(47369),ce=e.n(Oe),G=e(94184),f=e.n(G),l=function(R){var K=R.label,P=R.tooltip,ue=R.ellipsis,te=R.subTitle,de=(0,v.useContext)(O.ZP.ConfigContext),Fe=de.getPrefixCls;if(!P&&!te)return v.createElement(v.Fragment,null,K);var H=Fe("pro-core-label-tip"),Q=typeof P=="string"||v.isValidElement(P)?{title:P}:P,X=(Q==null?void 0:Q.icon)||v.createElement($.Z,null);return v.createElement("div",{className:H,onMouseDown:function(L){return L.stopPropagation()},onMouseLeave:function(L){return L.stopPropagation()},onMouseMove:function(L){return L.stopPropagation()}},v.createElement("div",{className:f()("".concat(H,"-title"),(0,x.Z)({},"".concat(H,"-title-ellipsis"),ue))},K),te&&v.createElement("div",{className:"".concat(H,"-subtitle")},te),P&&v.createElement(g.Z,Q,v.createElement("span",{className:"".concat(H,"-icon")},X)))};j.Z=v.memo(l)},34687:function(ie){ie.exports={container:"container___1sYa-",lang:"lang___l6cji",content:"content___2zk1-",icon:"icon___rzGKO"}},96138:function(){},32384:function(){},47369:function(){},3178:function(){},38120:function(ie,j,e){"use strict";e.r(j),e.d(j,{default:function(){return nt}});var A=e(18106),g=e(90642),x=e(34792),t=e(48086),O=e(11849),v=e(3182),$=e(2824),Oe=e(17462),ce=e(76772),G=e(94043),f=e.n(G),l=e(28991),a=e(67294),R={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M308.6 545.7c-19.8 2-57.1 10.7-77.4 28.6-61 53-24.5 150 99 150 71.8 0 143.5-45.7 199.8-119-80.2-38.9-148.1-66.8-221.4-59.6zm460.5 67c100.1 33.4 154.7 43 166.7 44.8A445.9 445.9 0 00960 512c0-247.4-200.6-448-448-448S64 264.6 64 512s200.6 448 448 448c155.9 0 293.2-79.7 373.5-200.5-75.6-29.8-213.6-85-286.8-120.1-69.9 85.7-160.1 137.8-253.7 137.8-158.4 0-212.1-138.1-137.2-229 16.3-19.8 44.2-38.7 87.3-49.4 67.5-16.5 175 10.3 275.7 43.4 18.1-33.3 33.4-69.9 44.7-108.9H305.1V402h160v-56.2H271.3v-31.3h193.8v-80.1s0-13.5 13.7-13.5H557v93.6h191.7v31.3H557.1V402h156.4c-15 61.1-37.7 117.4-66.2 166.8 47.5 17.1 90.1 33.3 121.8 43.9z"}}]},name:"alipay-circle",theme:"outlined"},K=R,P=e(27029),ue=function(i,d){return a.createElement(P.Z,(0,l.Z)((0,l.Z)({},i),{},{ref:d,icon:K}))};ue.displayName="AlipayCircleOutlined";var te=a.forwardRef(ue),de={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M512 64C264.6 64 64 264.6 64 512s200.6 448 448 448 448-200.6 448-448S759.4 64 512 64zM315.7 291.5c27.3 0 49.5 22.1 49.5 49.4s-22.1 49.4-49.5 49.4a49.4 49.4 0 110-98.8zM366.9 578c-13.6 42.3-10.2 26.7-64.4 144.5l-78.5-49s87.7-79.8 105.6-116.2c19.2-38.4-21.1-58.9-21.1-58.9l-60.2-37.5 32.7-50.2c45.4 33.7 48.7 36.6 79.2 67.2 23.8 23.9 20.7 56.8 6.7 100.1zm427.2 55c-15.3 143.8-202.4 90.3-202.4 90.3l10.2-41.1 43.3 9.3c80 5 72.3-64.9 72.3-64.9V423c.6-77.3-72.6-85.4-204.2-38.3l30.6 8.3c-2.5 9-12.5 23.2-25.2 38.6h176v35.6h-99.1v44.5h98.7v35.7h-98.7V622c14.9-4.8 28.6-11.5 40.5-20.5l-8.7-32.5 46.5-14.4 38.8 94.9-57.3 23.9-10.2-37.8c-25.6 19.5-78.8 48-171.8 45.4-99.2 2.6-73.7-112-73.7-112l2.5-1.3H472c-.5 14.7-6.6 38.7 1.7 51.8 6.8 10.8 24.2 12.6 35.3 13.1 1.3.1 2.6.1 3.9.1v-85.3h-101v-35.7h101v-44.5H487c-22.7 24.1-43.5 44.1-43.5 44.1l-30.6-26.7c21.7-22.9 43.3-59.1 56.8-83.2-10.9 4.4-22 9.2-33.6 14.2-11.2 14.3-24.2 29-38.7 43.5.5.8-50-28.4-50-28.4 52.2-44.4 81.4-139.9 81.4-139.9l72.5 20.4s-5.9 14-18.4 35.6c290.3-82.3 307.4 50.5 307.4 50.5s19.1 91.8 3.8 235.7z"}}]},name:"taobao-circle",theme:"outlined"},Fe=de,H=function(i,d){return a.createElement(P.Z,(0,l.Z)((0,l.Z)({},i),{},{ref:d,icon:Fe}))};H.displayName="TaobaoCircleOutlined";var Q=a.forwardRef(H),X={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M512 64C264.6 64 64 264.6 64 512s200.6 448 448 448 448-200.6 448-448S759.4 64 512 64zm-44.4 672C353.1 736 236 680.4 236 588.9c0-47.8 30.2-103.1 82.3-155.3 69.5-69.6 150.6-101.4 181.1-70.8 13.5 13.5 14.8 36.8 6.1 64.6-4.5 14 13.1 6.3 13.1 6.3 56.2-23.6 105.2-25 123.1.7 9.6 13.7 8.6 32.8-.2 55.1-4.1 10.2 1.3 11.8 9 14.1 31.7 9.8 66.9 33.6 66.9 75.5.2 69.5-99.7 156.9-249.8 156.9zm207.3-290.8a34.9 34.9 0 00-7.2-34.1 34.68 34.68 0 00-33.1-10.7 18.24 18.24 0 01-7.6-35.7c24.1-5.1 50.1 2.3 67.7 21.9 17.7 19.6 22.4 46.3 14.9 69.8a18.13 18.13 0 01-22.9 11.7 18.18 18.18 0 01-11.8-22.9zm106 34.3s0 .1 0 0a21.1 21.1 0 01-26.6 13.7 21.19 21.19 0 01-13.6-26.7c11-34.2 4-73.2-21.7-101.8a104.04 104.04 0 00-98.9-32.1 21.14 21.14 0 01-25.1-16.3 21.07 21.07 0 0116.2-25.1c49.4-10.5 102.8 4.8 139.1 45.1 36.3 40.2 46.1 95.1 30.6 143.2zm-334.5 6.1c-91.4 9-160.7 65.1-154.7 125.2 5.9 60.1 84.8 101.5 176.2 92.5 91.4-9.1 160.7-65.1 154.7-125.3-5.9-60.1-84.8-101.5-176.2-92.4zm80.2 141.7c-18.7 42.3-72.3 64.8-117.8 50.1-43.9-14.2-62.5-57.7-43.3-96.8 18.9-38.4 68-60.1 111.5-48.8 45 11.7 68 54.2 49.6 95.5zm-93-32.2c-14.2-5.9-32.4.2-41.2 13.9-8.8 13.8-4.7 30.2 9.3 36.6 14.3 6.5 33.2.3 42-13.8 8.8-14.3 4.2-30.6-10.1-36.7zm34.9-14.5c-5.4-2.2-12.2.5-15.4 5.8-3.1 5.4-1.4 11.5 4.1 13.8 5.5 2.3 12.6-.3 15.8-5.8 3-5.6 1-11.8-4.5-13.8z"}}]},name:"weibo-circle",theme:"outlined"},N=X,L=function(i,d){return a.createElement(P.Z,(0,l.Z)((0,l.Z)({},i),{},{ref:d,icon:N}))};L.displayName="WeiboCircleOutlined";var ve=a.forwardRef(L),ae={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M832 464h-68V240c0-70.7-57.3-128-128-128H388c-70.7 0-128 57.3-128 128v224h-68c-17.7 0-32 14.3-32 32v384c0 17.7 14.3 32 32 32h640c17.7 0 32-14.3 32-32V496c0-17.7-14.3-32-32-32zM332 240c0-30.9 25.1-56 56-56h248c30.9 0 56 25.1 56 56v224H332V240zm460 600H232V536h560v304zM484 701v53c0 4.4 3.6 8 8 8h40c4.4 0 8-3.6 8-8v-53a48.01 48.01 0 10-56 0z"}}]},name:"lock",theme:"outlined"},h=ae,Te=function(i,d){return a.createElement(P.Z,(0,l.Z)((0,l.Z)({},i),{},{ref:d,icon:h}))};Te.displayName="LockOutlined";var M=a.forwardRef(Te),u={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M744 62H280c-35.3 0-64 28.7-64 64v768c0 35.3 28.7 64 64 64h464c35.3 0 64-28.7 64-64V126c0-35.3-28.7-64-64-64zm-8 824H288V134h448v752zM472 784a40 40 0 1080 0 40 40 0 10-80 0z"}}]},name:"mobile",theme:"outlined"},C=u,c=function(i,d){return a.createElement(P.Z,(0,l.Z)((0,l.Z)({},i),{},{ref:d,icon:C}))};c.displayName="MobileOutlined";var E=a.forwardRef(c),b=e(89366),F=e(22122),me=e(84305),fe=e(39559),w=e(81253),k=e(5894),Ne=e(7381),Ae=e(32384),pe=["logo","message","title","subTitle","actions","children"];function ne(r){var i=r.logo,d=r.message,I=r.title,z=r.subTitle,Ce=r.actions,Ee=r.children,ee=(0,w.Z)(r,pe),Pe=(0,Ne.YB)(),ye=(0,l.Z)({searchConfig:{submitText:Pe.getMessage("loginForm.submitText","\u767B\u5F55")},render:function(We,Ze){return Ze.pop()},submitButtonProps:{size:"large",style:{width:"100%"}}},ee.submitter),Me=(0,a.useContext)(fe.ZP.ConfigContext),ze=Me.getPrefixCls("pro-form-login"),m=function(We){return"".concat(ze,"-").concat(We)},le=(0,a.useMemo)(function(){return i?typeof i=="string"?a.createElement("img",{src:i}):i:null},[i]);return a.createElement("div",{className:m("container")},a.createElement("div",{className:m("top")},I||le?a.createElement("div",{className:m("header")},le?a.createElement("span",{className:m("logo")},le):null,I?a.createElement("span",{className:m("title")},I):null):null,z?a.createElement("div",{className:m("desc")},z):null),a.createElement("div",{className:m("main")},a.createElement(k.A,(0,F.Z)({isKeyPressSubmit:!0,submitter:ye},ee),d,Ee),Ce?a.createElement("div",{className:m("other")},Ce):null))}var Re=e(9715),ge=e(93766),Ue=e(57663),be=e(71577),Le=e(47673),re=e(4107),$e=e(87757),se=e.n($e),q=e(92137),he=e(28481),Ie=e(64893),De=["rules","name","phoneName","fieldProps","captchaTextRender","captchaProps"],Se=a.forwardRef(function(r,i){var d=(0,a.useState)(r.countDown||60),I=(0,he.Z)(d,2),z=I[0],Ce=I[1],Ee=(0,a.useState)(!1),ee=(0,he.Z)(Ee,2),Pe=ee[0],ye=ee[1],Me=(0,a.useState)(),ze=(0,he.Z)(Me,2),m=ze[0],le=ze[1],Ye=r.rules,We=r.name,Ze=r.phoneName,W=r.fieldProps,Z=r.captchaTextRender,D=Z===void 0?function(T,J){return T?"".concat(J," \u79D2\u540E\u91CD\u65B0\u83B7\u53D6"):"\u83B7\u53D6\u9A8C\u8BC1\u7801"}:Z,S=r.captchaProps,Y=(0,w.Z)(r,De),p=function(){var T=(0,q.Z)(se().mark(function J(xe){return se().wrap(function(U){for(;;)switch(U.prev=U.next){case 0:return U.prev=0,le(!0),U.next=4,Y.onGetCaptcha(xe);case 4:le(!1),ye(!0),U.next=13;break;case 8:U.prev=8,U.t0=U.catch(0),ye(!1),le(!1),console.log(U.t0);case 13:case"end":return U.stop()}},J,null,[[0,8]])}));return function(xe){return T.apply(this,arguments)}}();return(0,a.useEffect)(function(){var T=0,J=r.countDown;return Pe&&(T=window.setInterval(function(){Ce(function(xe){return xe<=1?(ye(!1),clearInterval(T),J||60):xe-1})},1e3)),function(){return clearInterval(T)}},[Pe]),a.createElement(ge.Z.Item,{noStyle:!0,shouldUpdate:!0},function(T){var J=T.getFieldValue,xe=T.validateFields;return a.createElement("div",{style:(0,l.Z)((0,l.Z)({},W==null?void 0:W.style),{},{display:"flex",alignItems:"center"}),ref:i},a.createElement(re.Z,(0,F.Z)({},W,{style:{flex:1,transition:"width .3s",marginRight:8}})),a.createElement(be.Z,(0,F.Z)({style:{display:"block"},disabled:Pe,loading:m},S,{onClick:(0,q.Z)(se().mark(function y(){var U;return se().wrap(function(B){for(;;)switch(B.prev=B.next){case 0:if(B.prev=0,!Ze){B.next=9;break}return B.next=4,xe([Ze].flat(1));case 4:return U=J([Ze].flat(1)),B.next=7,p(U);case 7:B.next=11;break;case 9:return B.next=11,p("");case 11:B.next=16;break;case 13:B.prev=13,B.t0=B.catch(0),console.log(B.t0);case 16:case"end":return B.stop()}},y,null,[[0,13]])}))}),D(Pe,z)))})}),Ke=(0,Ie.G)(Se),o=e(63185),Be=e(9676),je=e(98757),Je=e(22270),He=["options","fieldProps","proFieldProps","valueEnum"],Qe=a.forwardRef(function(r,i){var d=r.options,I=r.fieldProps,z=r.proFieldProps,Ce=r.valueEnum,Ee=(0,w.Z)(r,He);return a.createElement(je.Z,(0,F.Z)({ref:i,valueType:"checkbox",mode:"edit",valueEnum:(0,Je.h)(Ce,void 0),fieldProps:(0,l.Z)({options:d},I),proFieldProps:z},Ee))}),Xe=a.forwardRef(function(r,i){var d=r.fieldProps,I=r.children;return a.createElement(Be.Z,(0,F.Z)({ref:i},d),I)}),Ge=(0,Ie.G)(Xe,{valuePropName:"checked"}),Ve=Ge;Ve.Group=Qe;var we=Ve,V=e(5966),s=e(21010),oe=e(73631),_e=e(5029);function et(r,i){return ke.apply(this,arguments)}function ke(){return ke=(0,v.Z)(f().mark(function r(i,d){return f().wrap(function(z){for(;;)switch(z.prev=z.next){case 0:return z.abrupt("return",(0,s.WY)("/api/login/captcha",(0,O.Z)({method:"GET",params:(0,O.Z)({},i)},d||{})));case 1:case"end":return z.stop()}},r)})),ke.apply(this,arguments)}var tt=e(34687),_=e.n(tt),n=e(85893),qe=function(i){var d=i.content;return(0,n.jsx)(ce.Z,{style:{marginBottom:24},message:d,type:"error",showIcon:!0})},at=function(){var i=(0,a.useState)({}),d=(0,$.Z)(i,2),I=d[0],z=d[1],Ce=(0,a.useState)("account"),Ee=(0,$.Z)(Ce,2),ee=Ee[0],Pe=Ee[1],ye=(0,s.tT)("@@initialState"),Me=ye.initialState,ze=ye.setInitialState,m=(0,s.YB)(),le=function(){var W=(0,v.Z)(f().mark(function Z(){var D,S;return f().wrap(function(p){for(;;)switch(p.prev=p.next){case 0:return p.next=2,Me==null||(D=Me.fetchUserInfo)===null||D===void 0?void 0:D.call(Me);case 2:if(S=p.sent,!S){p.next=6;break}return p.next=6,ze(function(T){return(0,O.Z)((0,O.Z)({},T),{},{currentUser:S})});case 6:case"end":return p.stop()}},Z)}));return function(){return W.apply(this,arguments)}}(),Ye=function(){var W=(0,v.Z)(f().mark(function Z(D){var S,Y,p,T,J;return f().wrap(function(y){for(;;)switch(y.prev=y.next){case 0:return y.prev=0,y.next=3,(0,_e.x4)((0,O.Z)((0,O.Z)({},D),{},{type:ee}));case 3:if(S=y.sent,S.status!=="ok"){y.next=15;break}return Y=m.formatMessage({id:"pages.login.success",defaultMessage:"\u767B\u5F55\u6210\u529F\uFF01"}),t.default.success(Y),y.next=9,le();case 9:if(s.m8){y.next=11;break}return y.abrupt("return");case 11:return p=s.m8.location.query,T=p.redirect,s.m8.push(T||"/"),y.abrupt("return");case 15:console.log(S),z(S),y.next=23;break;case 19:y.prev=19,y.t0=y.catch(0),J=m.formatMessage({id:"pages.login.failure",defaultMessage:"\u767B\u5F55\u5931\u8D25\uFF0C\u8BF7\u91CD\u8BD5\uFF01"}),t.default.error(J);case 23:case"end":return y.stop()}},Z,null,[[0,19]])}));return function(D){return W.apply(this,arguments)}}(),We=I.status,Ze=I.type;return(0,n.jsxs)("div",{className:_().container,children:[(0,n.jsx)("div",{className:_().lang,"data-lang":!0,children:s.pD&&(0,n.jsx)(s.pD,{})}),(0,n.jsx)("div",{className:_().content,children:(0,n.jsxs)(ne,{logo:(0,n.jsx)("img",{alt:"logo",src:"/logo.svg"}),title:"Ant Design",subTitle:m.formatMessage({id:"pages.layouts.userLayout.title"}),initialValues:{autoLogin:!0},actions:[(0,n.jsx)(s._H,{id:"pages.login.loginWith",defaultMessage:"\u5176\u4ED6\u767B\u5F55\u65B9\u5F0F"},"loginWith"),(0,n.jsx)(te,{className:_().icon},"AlipayCircleOutlined"),(0,n.jsx)(Q,{className:_().icon},"TaobaoCircleOutlined"),(0,n.jsx)(ve,{className:_().icon},"WeiboCircleOutlined")],onFinish:function(){var W=(0,v.Z)(f().mark(function Z(D){return f().wrap(function(Y){for(;;)switch(Y.prev=Y.next){case 0:return Y.next=2,Ye(D);case 2:case"end":return Y.stop()}},Z)}));return function(Z){return W.apply(this,arguments)}}(),children:[(0,n.jsxs)(g.Z,{activeKey:ee,onChange:Pe,children:[(0,n.jsx)(g.Z.TabPane,{tab:m.formatMessage({id:"pages.login.accountLogin.tab",defaultMessage:"\u8D26\u6237\u5BC6\u7801\u767B\u5F55"})},"account"),(0,n.jsx)(g.Z.TabPane,{tab:m.formatMessage({id:"pages.login.phoneLogin.tab",defaultMessage:"\u624B\u673A\u53F7\u767B\u5F55"})},"mobile")]}),We==="error"&&Ze==="account"&&(0,n.jsx)(qe,{content:m.formatMessage({id:"pages.login.accountLogin.errorMessage",defaultMessage:"\u8D26\u6237\u6216\u5BC6\u7801\u9519\u8BEF(admin/ant.design)"})}),ee==="account"&&(0,n.jsxs)(n.Fragment,{children:[(0,n.jsx)(V.Z,{name:"username",fieldProps:{size:"large",prefix:(0,n.jsx)(b.Z,{className:_().prefixIcon})},placeholder:m.formatMessage({id:"pages.login.username.placeholder",defaultMessage:"\u7528\u6237\u540D: admin or user"}),rules:[{required:!0,message:(0,n.jsx)(s._H,{id:"pages.login.username.required",defaultMessage:"\u8BF7\u8F93\u5165\u7528\u6237\u540D!"})}]}),(0,n.jsx)(V.Z.Password,{name:"password",fieldProps:{size:"large",prefix:(0,n.jsx)(M,{className:_().prefixIcon})},placeholder:m.formatMessage({id:"pages.login.password.placeholder",defaultMessage:"\u5BC6\u7801: ant.design"}),rules:[{required:!0,message:(0,n.jsx)(s._H,{id:"pages.login.password.required",defaultMessage:"\u8BF7\u8F93\u5165\u5BC6\u7801\uFF01"})}]})]}),We==="error"&&Ze==="mobile"&&(0,n.jsx)(qe,{content:"\u9A8C\u8BC1\u7801\u9519\u8BEF"}),ee==="mobile"&&(0,n.jsxs)(n.Fragment,{children:[(0,n.jsx)(V.Z,{fieldProps:{size:"large",prefix:(0,n.jsx)(E,{className:_().prefixIcon})},name:"mobile",placeholder:m.formatMessage({id:"pages.login.phoneNumber.placeholder",defaultMessage:"\u624B\u673A\u53F7"}),rules:[{required:!0,message:(0,n.jsx)(s._H,{id:"pages.login.phoneNumber.required",defaultMessage:"\u8BF7\u8F93\u5165\u624B\u673A\u53F7\uFF01"})},{pattern:/^1\d{10}$/,message:(0,n.jsx)(s._H,{id:"pages.login.phoneNumber.invalid",defaultMessage:"\u624B\u673A\u53F7\u683C\u5F0F\u9519\u8BEF\uFF01"})}]}),(0,n.jsx)(Ke,{fieldProps:{size:"large",prefix:(0,n.jsx)(M,{className:_().prefixIcon})},captchaProps:{size:"large"},placeholder:m.formatMessage({id:"pages.login.captcha.placeholder",defaultMessage:"\u8BF7\u8F93\u5165\u9A8C\u8BC1\u7801"}),captchaTextRender:function(Z,D){return Z?"".concat(D," ").concat(m.formatMessage({id:"pages.getCaptchaSecondText",defaultMessage:"\u83B7\u53D6\u9A8C\u8BC1\u7801"})):m.formatMessage({id:"pages.login.phoneLogin.getVerificationCode",defaultMessage:"\u83B7\u53D6\u9A8C\u8BC1\u7801"})},name:"captcha",rules:[{required:!0,message:(0,n.jsx)(s._H,{id:"pages.login.captcha.required",defaultMessage:"\u8BF7\u8F93\u5165\u9A8C\u8BC1\u7801\uFF01"})}],onGetCaptcha:function(){var W=(0,v.Z)(f().mark(function Z(D){var S;return f().wrap(function(p){for(;;)switch(p.prev=p.next){case 0:return p.next=2,et({phone:D});case 2:if(S=p.sent,S!==!1){p.next=5;break}return p.abrupt("return");case 5:t.default.success("\u83B7\u53D6\u9A8C\u8BC1\u7801\u6210\u529F\uFF01\u9A8C\u8BC1\u7801\u4E3A\uFF1A1234");case 6:case"end":return p.stop()}},Z)}));return function(Z){return W.apply(this,arguments)}}()})]}),(0,n.jsxs)("div",{style:{marginBottom:24},children:[(0,n.jsx)(we,{noStyle:!0,name:"autoLogin",children:(0,n.jsx)(s._H,{id:"pages.login.rememberMe",defaultMessage:"\u81EA\u52A8\u767B\u5F55"})}),(0,n.jsx)("a",{style:{float:"right"},children:(0,n.jsx)(s._H,{id:"pages.login.forgotPassword",defaultMessage:"\u5FD8\u8BB0\u5BC6\u7801"})})]})]})}),(0,n.jsx)(oe.Z,{})]})},nt=at},76772:function(ie,j,e){"use strict";e.d(j,{Z:function(){return Te}});var A=e(22122),g=e(96156),x=e(28481),t=e(67294),O=e(54549),v=e(15873),$=e(57119),Oe=e(68628),ce=e(73218),G=e(38819),f=e(68855),l=e(40847),a=e(43061),R=e(60444),K=e(94184),P=e.n(K),ue=e(65632),te=e(5467),de=e(6610),Fe=e(5991),H=e(10379),Q=e(60446),X=function(M){(0,H.Z)(C,M);var u=(0,Q.Z)(C);function C(){var c;return(0,de.Z)(this,C),c=u.apply(this,arguments),c.state={error:void 0,info:{componentStack:""}},c}return(0,Fe.Z)(C,[{key:"componentDidCatch",value:function(E,b){this.setState({error:E,info:b})}},{key:"render",value:function(){var E=this.props,b=E.message,F=E.description,me=E.children,fe=this.state,w=fe.error,k=fe.info,Ne=k&&k.componentStack?k.componentStack:null,Ae=typeof b=="undefined"?(w||"").toString():b,pe=typeof F=="undefined"?Ne:F;return w?t.createElement(Te,{type:"error",message:Ae,description:t.createElement("pre",null,pe)}):me}}]),C}(t.Component),N=e(96159),L=function(M,u){var C={};for(var c in M)Object.prototype.hasOwnProperty.call(M,c)&&u.indexOf(c)<0&&(C[c]=M[c]);if(M!=null&&typeof Object.getOwnPropertySymbols=="function")for(var E=0,c=Object.getOwnPropertySymbols(M);E<c.length;E++)u.indexOf(c[E])<0&&Object.prototype.propertyIsEnumerable.call(M,c[E])&&(C[c[E]]=M[c[E]]);return C},ve={success:G.Z,info:l.Z,error:a.Z,warning:f.Z},ae={success:v.Z,info:Oe.Z,error:ce.Z,warning:$.Z},h=function(u){var C,c=u.description,E=u.prefixCls,b=u.message,F=u.banner,me=u.className,fe=me===void 0?"":me,w=u.style,k=u.onMouseEnter,Ne=u.onMouseLeave,Ae=u.onClick,pe=u.afterClose,ne=u.showIcon,Re=u.closable,ge=u.closeText,Ue=u.closeIcon,be=Ue===void 0?t.createElement(O.Z,null):Ue,Le=u.action,re=L(u,["description","prefixCls","message","banner","className","style","onMouseEnter","onMouseLeave","onClick","afterClose","showIcon","closable","closeText","closeIcon","action"]),$e=t.useState(!1),se=(0,x.Z)($e,2),q=se[0],he=se[1],Ie=t.useRef(),De=t.useContext(ue.E_),Se=De.getPrefixCls,Ke=De.direction,o=Se("alert",E),Be=function(s){var oe;he(!0),(oe=re.onClose)===null||oe===void 0||oe.call(re,s)},je=function(){var s=re.type;return s!==void 0?s:F?"warning":"info"},Je=ge?!0:Re,He=je(),Qe=function(){var s=re.icon,oe=(c?ae:ve)[He]||null;return s?(0,N.wm)(s,t.createElement("span",{className:"".concat(o,"-icon")},s),function(){return{className:P()("".concat(o,"-icon"),(0,g.Z)({},s.props.className,s.props.className))}}):t.createElement(oe,{className:"".concat(o,"-icon")})},Xe=function(){return Je?t.createElement("button",{type:"button",onClick:Be,className:"".concat(o,"-close-icon"),tabIndex:0},ge?t.createElement("span",{className:"".concat(o,"-close-text")},ge):be):null},Ge=F&&ne===void 0?!0:ne,Ve=P()(o,"".concat(o,"-").concat(He),(C={},(0,g.Z)(C,"".concat(o,"-with-description"),!!c),(0,g.Z)(C,"".concat(o,"-no-icon"),!Ge),(0,g.Z)(C,"".concat(o,"-banner"),!!F),(0,g.Z)(C,"".concat(o,"-rtl"),Ke==="rtl"),C),fe),we=(0,te.Z)(re);return t.createElement(R.Z,{visible:!q,motionName:"".concat(o,"-motion"),motionAppear:!1,motionEnter:!1,onLeaveStart:function(s){return{maxHeight:s.offsetHeight}},onLeaveEnd:pe},function(V){var s=V.className,oe=V.style;return t.createElement("div",(0,A.Z)({ref:Ie,"data-show":!q,className:P()(Ve,s),style:(0,A.Z)((0,A.Z)({},w),oe),onMouseEnter:k,onMouseLeave:Ne,onClick:Ae,role:"alert"},we),Ge?Qe():null,t.createElement("div",{className:"".concat(o,"-content")},b?t.createElement("div",{className:"".concat(o,"-message")},b):null,c?t.createElement("div",{className:"".concat(o,"-description")},c):null),Le?t.createElement("div",{className:"".concat(o,"-action")},Le):null,Xe())})};h.ErrorBoundary=X;var Te=h},17462:function(ie,j,e){"use strict";var A=e(38663),g=e.n(A),x=e(3178),t=e.n(x)},97435:function(ie,j){"use strict";function e(A,g){for(var x=Object.assign({},A),t=0;t<g.length;t+=1){var O=g[t];delete x[O]}return x}j.Z=e}}]);