(self.webpackChunkant_design_pro=self.webpackChunkant_design_pro||[]).push([[5362],{64335:function(ae,I,o){"use strict";var M=o(67294),Z=(0,M.createContext)({});I.Z=Z},85224:function(ae,I,o){"use strict";var M=o(22122),Z=o(28991),P=o(84305),B=o(39559),F=o(81253),D=o(67294),y=o(94184),$=o.n(y),w=o(97435),a=o(56264),T=o.n(a),H=o(64335),Q=["children","className","extra","style","renderContent"],_=function(A){var K=A.children,G=A.className,j=A.extra,oe=A.style,X=A.renderContent,ue=(0,F.Z)(A,Q),fe=(0,D.useContext)(B.ZP.ConfigContext),ve=fe.getPrefixCls,V=A.prefixCls||ve("pro"),me="".concat(V,"-footer-bar"),b=(0,D.useContext)(H.Z),ie=(0,D.useMemo)(function(){var q=b.hasSiderMenu,k=b.isMobile,ee=b.siderWidth;if(!!q)return ee?k?"100%":"calc(100% - ".concat(ee,"px)"):"100%"},[b.collapsed,b.hasSiderMenu,b.isMobile,b.siderWidth]),le=D.createElement(D.Fragment,null,D.createElement("div",{className:"".concat(me,"-left")},j),D.createElement("div",{className:"".concat(me,"-right")},K));return(0,D.useEffect)(function(){return!b||!(b==null?void 0:b.setHasFooterToolbar)?function(){}:(b==null||b.setHasFooterToolbar(!0),function(){var q;b==null||(q=b.setHasFooterToolbar)===null||q===void 0||q.call(b,!1)})},[]),D.createElement("div",(0,M.Z)({className:$()(G,"".concat(me)),style:(0,Z.Z)({width:ie},oe)},(0,w.Z)(ue,["prefixCls"])),X?X((0,Z.Z)((0,Z.Z)((0,Z.Z)({},A),b),{},{leftWidth:ie}),le):le)};I.Z=_},21349:function(ae,I,o){"use strict";var M=o(84305),Z=o(39559),P=o(53645),B=o.n(P),F=o(67294),D=o(94184),y=o.n(D),$=o(64335),w=function(T){var H=(0,F.useContext)($.Z),Q=T.children,_=T.contentWidth,L=T.className,A=T.style,K=(0,F.useContext)(Z.ZP.ConfigContext),G=K.getPrefixCls,j=T.prefixCls||G("pro"),oe=_||H.contentWidth,X="".concat(j,"-grid-content");return F.createElement("div",{className:y()(X,L,{wide:oe==="Fixed"}),style:A},F.createElement("div",{className:"".concat(j,"-grid-content-children")},Q))};I.Z=w},75362:function(ae,I,o){"use strict";o.d(I,{ZP:function(){return Tt}});var M=o(38663),Z=o(70883),P=o(22122),B=o(96156),F=o(6610),D=o(5991),y=o(10379),$=o(60446),w=o(90484),a=o(67294),T=o(94184),H=o.n(T),Q=o(98423),_=o(48717),L=o(65632),A=o(85061),K=o(75164);function G(i){var e,r=function(l){return function(){e=null,i.apply(void 0,(0,A.Z)(l))}},t=function(){if(e==null){for(var l=arguments.length,c=new Array(l),s=0;s<l;s++)c[s]=arguments[s];e=(0,K.Z)(r(c))}};return t.cancel=function(){return K.Z.cancel(e)},t}function j(){return function(e,r,t){var n=t.value,l=!1;return{configurable:!0,get:function(){if(l||this===e.prototype||this.hasOwnProperty(r))return n;var s=G(n.bind(this));return l=!0,Object.defineProperty(this,r,{value:s,configurable:!0,writable:!0}),l=!1,s}}}}var oe=o(64019);function X(i){return i!==window?i.getBoundingClientRect():{top:0,bottom:window.innerHeight}}function ue(i,e,r){if(r!==void 0&&e.top>i.top-r)return r+e.top}function fe(i,e,r){if(r!==void 0&&e.bottom<i.bottom+r){var t=window.innerHeight-e.bottom;return r+t}}var ve=["resize","scroll","touchstart","touchmove","touchend","pageshow","load"],V=[];function me(){return V}function b(i,e){if(!!i){var r=V.find(function(t){return t.target===i});r?r.affixList.push(e):(r={target:i,affixList:[e],eventHandlers:{}},V.push(r),ve.forEach(function(t){r.eventHandlers[t]=(0,oe.Z)(i,t,function(){r.affixList.forEach(function(n){n.lazyUpdatePosition()})})}))}}function ie(i){var e=V.find(function(r){var t=r.affixList.some(function(n){return n===i});return t&&(r.affixList=r.affixList.filter(function(n){return n!==i})),t});e&&e.affixList.length===0&&(V=V.filter(function(r){return r!==e}),ve.forEach(function(r){var t=e.eventHandlers[r];t&&t.remove&&t.remove()}))}var le=function(i,e,r,t){var n=arguments.length,l=n<3?e:t===null?t=Object.getOwnPropertyDescriptor(e,r):t,c;if((typeof Reflect=="undefined"?"undefined":(0,w.Z)(Reflect))==="object"&&typeof Reflect.decorate=="function")l=Reflect.decorate(i,e,r,t);else for(var s=i.length-1;s>=0;s--)(c=i[s])&&(l=(n<3?c(l):n>3?c(e,r,l):c(e,r))||l);return n>3&&l&&Object.defineProperty(e,r,l),l};function q(){return typeof window!="undefined"?window:null}var k;(function(i){i[i.None=0]="None",i[i.Prepare=1]="Prepare"})(k||(k={}));var ee=function(i){(0,y.Z)(r,i);var e=(0,$.Z)(r);function r(){var t;return(0,F.Z)(this,r),t=e.apply(this,arguments),t.state={status:k.None,lastAffix:!1,prevTarget:null},t.getOffsetTop=function(){var n=t.props,l=n.offsetBottom,c=n.offsetTop;return l===void 0&&c===void 0?0:c},t.getOffsetBottom=function(){return t.props.offsetBottom},t.savePlaceholderNode=function(n){t.placeholderNode=n},t.saveFixedNode=function(n){t.fixedNode=n},t.measure=function(){var n=t.state,l=n.status,c=n.lastAffix,s=t.props.onChange,u=t.getTargetFunc();if(!(l!==k.Prepare||!t.fixedNode||!t.placeholderNode||!u)){var f=t.getOffsetTop(),m=t.getOffsetBottom(),h=u();if(!!h){var d={status:k.None},g=X(h),v=X(t.placeholderNode),C=ue(v,g,f),p=fe(v,g,m);C!==void 0?(d.affixStyle={position:"fixed",top:C,width:v.width,height:v.height},d.placeholderStyle={width:v.width,height:v.height}):p!==void 0&&(d.affixStyle={position:"fixed",bottom:p,width:v.width,height:v.height},d.placeholderStyle={width:v.width,height:v.height}),d.lastAffix=!!d.affixStyle,s&&c!==d.lastAffix&&s(d.lastAffix),t.setState(d)}}},t.prepareMeasure=function(){if(t.setState({status:k.Prepare,affixStyle:void 0,placeholderStyle:void 0}),!1)var n},t}return(0,D.Z)(r,[{key:"getTargetFunc",value:function(){var n=this.context.getTargetContainer,l=this.props.target;return l!==void 0?l:n||q}},{key:"componentDidMount",value:function(){var n=this,l=this.getTargetFunc();l&&(this.timeout=setTimeout(function(){b(l(),n),n.updatePosition()}))}},{key:"componentDidUpdate",value:function(n){var l=this.state.prevTarget,c=this.getTargetFunc(),s=(c==null?void 0:c())||null;l!==s&&(ie(this),s&&(b(s,this),this.updatePosition()),this.setState({prevTarget:s})),(n.offsetTop!==this.props.offsetTop||n.offsetBottom!==this.props.offsetBottom)&&this.updatePosition(),this.measure()}},{key:"componentWillUnmount",value:function(){clearTimeout(this.timeout),ie(this),this.updatePosition.cancel(),this.lazyUpdatePosition.cancel()}},{key:"updatePosition",value:function(){this.prepareMeasure()}},{key:"lazyUpdatePosition",value:function(){var n=this.getTargetFunc(),l=this.state.affixStyle;if(n&&l){var c=this.getOffsetTop(),s=this.getOffsetBottom(),u=n();if(u&&this.placeholderNode){var f=X(u),m=X(this.placeholderNode),h=ue(m,f,c),d=fe(m,f,s);if(h!==void 0&&l.top===h||d!==void 0&&l.bottom===d)return}}this.prepareMeasure()}},{key:"render",value:function(){var n=this,l=this.context.getPrefixCls,c=this.state,s=c.affixStyle,u=c.placeholderStyle,f=this.props,m=f.prefixCls,h=f.children,d=H()((0,B.Z)({},l("affix",m),!!s)),g=(0,Q.Z)(this.props,["prefixCls","offsetTop","offsetBottom","target","onChange"]);return a.createElement(_.Z,{onResize:function(){n.updatePosition()}},a.createElement("div",(0,P.Z)({},g,{ref:this.savePlaceholderNode}),s&&a.createElement("div",{style:u,"aria-hidden":"true"}),a.createElement("div",{className:d,ref:this.saveFixedNode,style:s},a.createElement(_.Z,{onResize:function(){n.updatePosition()}},h))))}}]),r}(a.Component);ee.contextType=L.E_,le([j()],ee.prototype,"updatePosition",null),le([j()],ee.prototype,"lazyUpdatePosition",null);var We=ee,zt=o(84305),Ee=o(39559),$t=o(59903),jt=o(81262),_t=o(30887),Gt=o(59250),Xt=o(94233),Pe=o(28481),S=o(28991),Fe={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M872 474H286.9l350.2-304c5.6-4.9 2.2-14-5.2-14h-88.5c-3.9 0-7.6 1.4-10.5 3.9L155 487.8a31.96 31.96 0 000 48.3L535.1 866c1.5 1.3 3.3 2 5.2 2h91.5c7.4 0 10.8-9.2 5.2-14L286.9 550H872c4.4 0 8-3.6 8-8v-60c0-4.4-3.6-8-8-8z"}}]},name:"arrow-left",theme:"outlined"},we=Fe,be=o(27029),ye=function(e,r){return a.createElement(be.Z,(0,S.Z)((0,S.Z)({},e),{},{ref:r,icon:we}))};ye.displayName="ArrowLeftOutlined";var He=a.forwardRef(ye),Ke={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M869 487.8L491.2 159.9c-2.9-2.5-6.6-3.9-10.5-3.9h-88.5c-7.4 0-10.8 9.2-5.2 14l350.2 304H152c-4.4 0-8 3.6-8 8v60c0 4.4 3.6 8 8 8h585.1L386.9 854c-5.6 4.9-2.2 14 5.2 14h91.5c1.9 0 3.8-.7 5.2-2L869 536.2a32.07 32.07 0 000-48.4z"}}]},name:"arrow-right",theme:"outlined"},ze=Ke,xe=function(e,r){return a.createElement(be.Z,(0,S.Z)((0,S.Z)({},e),{},{ref:r,icon:ze}))};xe.displayName="ArrowRightOutlined";var $e=a.forwardRef(xe),je=o(50344),_e=o(57254),Ge=o(65514),Xe=function(i,e){var r={};for(var t in i)Object.prototype.hasOwnProperty.call(i,t)&&e.indexOf(t)<0&&(r[t]=i[t]);if(i!=null&&typeof Object.getOwnPropertySymbols=="function")for(var n=0,t=Object.getOwnPropertySymbols(i);n<t.length;n++)e.indexOf(t[n])<0&&Object.prototype.propertyIsEnumerable.call(i,t[n])&&(r[t[n]]=i[t[n]]);return r},pe=function(e){var r=e.prefixCls,t=e.separator,n=t===void 0?"/":t,l=e.children,c=e.overlay,s=e.dropdownProps,u=Xe(e,["prefixCls","separator","children","overlay","dropdownProps"]),f=a.useContext(L.E_),m=f.getPrefixCls,h=m("breadcrumb",r),d=function(C){return c?a.createElement(Ge.Z,(0,P.Z)({overlay:c,placement:"bottomCenter"},s),a.createElement("span",{className:"".concat(h,"-overlay-link")},C,a.createElement(_e.Z,null))):C},g;return"href"in u?g=a.createElement("a",(0,P.Z)({className:"".concat(h,"-link")},u),l):g=a.createElement("span",(0,P.Z)({className:"".concat(h,"-link")},u),l),g=d(g),l?a.createElement("span",null,g,n&&a.createElement("span",{className:"".concat(h,"-separator")},n)):null};pe.__ANT_BREADCRUMB_ITEM=!0;var Oe=pe,Re=function(e){var r=e.children,t=a.useContext(L.E_),n=t.getPrefixCls,l=n("breadcrumb");return a.createElement("span",{className:"".concat(l,"-separator")},r||"/")};Re.__ANT_BREADCRUMB_SEPARATOR=!0;var Ye=Re,Ne=o(54689),Ve=o(21687),ke=o(96159),Je=function(i,e){var r={};for(var t in i)Object.prototype.hasOwnProperty.call(i,t)&&e.indexOf(t)<0&&(r[t]=i[t]);if(i!=null&&typeof Object.getOwnPropertySymbols=="function")for(var n=0,t=Object.getOwnPropertySymbols(i);n<t.length;n++)e.indexOf(t[n])<0&&Object.prototype.propertyIsEnumerable.call(i,t[n])&&(r[t[n]]=i[t[n]]);return r};function Qe(i,e){if(!i.breadcrumbName)return null;var r=Object.keys(e).join("|"),t=i.breadcrumbName.replace(new RegExp(":(".concat(r,")"),"g"),function(n,l){return e[l]||n});return t}function qe(i,e,r,t){var n=r.indexOf(i)===r.length-1,l=Qe(i,e);return n?a.createElement("span",null,l):a.createElement("a",{href:"#/".concat(t.join("/"))},l)}var Ze=function(e,r){return e=(e||"").replace(/^\//,""),Object.keys(r).forEach(function(t){e=e.replace(":".concat(t),r[t])}),e},et=function(e,r,t){var n=(0,A.Z)(e),l=Ze(r||"",t);return l&&n.push(l),n},ge=function(e){var r=e.prefixCls,t=e.separator,n=t===void 0?"/":t,l=e.style,c=e.className,s=e.routes,u=e.children,f=e.itemRender,m=f===void 0?qe:f,h=e.params,d=h===void 0?{}:h,g=Je(e,["prefixCls","separator","style","className","routes","children","itemRender","params"]),v=a.useContext(L.E_),C=v.getPrefixCls,p=v.direction,x,O=C("breadcrumb",r);if(s&&s.length>0){var R=[];x=s.map(function(E){var N=Ze(E.path,d);N&&R.push(N);var z;return E.children&&E.children.length&&(z=a.createElement(Ne.Z,null,E.children.map(function(U){return a.createElement(Ne.Z.Item,{key:U.path||U.breadcrumbName},m(U,d,s,et(R,U.path,d)))}))),a.createElement(Oe,{overlay:z,separator:n,key:N||E.breadcrumbName},m(E,d,s,R))})}else u&&(x=(0,je.Z)(u).map(function(E,N){return E&&((0,Ve.Z)(E.type&&(E.type.__ANT_BREADCRUMB_ITEM===!0||E.type.__ANT_BREADCRUMB_SEPARATOR===!0),"Breadcrumb","Only accepts Breadcrumb.Item and Breadcrumb.Separator as it's children"),(0,ke.Tm)(E,{separator:n,key:N}))}));var Y=H()(O,(0,B.Z)({},"".concat(O,"-rtl"),p==="rtl"),c);return a.createElement("div",(0,P.Z)({className:Y,style:l},g),x)};ge.Item=Oe,ge.Separator=Ye;var tt=ge,rt=tt,at=o(51890),nt=o(34952),ot=o(42051),it=o(73577),lt=function(e,r,t){return!r||!t?null:a.createElement(ot.Z,{componentName:"PageHeader"},function(n){var l=n.back;return a.createElement("div",{className:"".concat(e,"-back")},a.createElement(nt.Z,{onClick:function(s){t==null||t(s)},className:"".concat(e,"-back-button"),"aria-label":l},r))})},st=function(e){return a.createElement(rt,e)},ct=function(e){var r=arguments.length>1&&arguments[1]!==void 0?arguments[1]:"ltr";return e.backIcon!==void 0?e.backIcon:r==="rtl"?a.createElement($e,null):a.createElement(He,null)},dt=function(e,r){var t=arguments.length>2&&arguments[2]!==void 0?arguments[2]:"ltr",n=r.title,l=r.avatar,c=r.subTitle,s=r.tags,u=r.extra,f=r.onBack,m="".concat(e,"-heading"),h=n||c||s||u;if(!h)return null;var d=ct(r,t),g=lt(e,d,f),v=g||l||h;return a.createElement("div",{className:m},v&&a.createElement("div",{className:"".concat(m,"-left")},g,l&&a.createElement(at.C,l),n&&a.createElement("span",{className:"".concat(m,"-title"),title:typeof n=="string"?n:void 0},n),c&&a.createElement("span",{className:"".concat(m,"-sub-title"),title:typeof c=="string"?c:void 0},c),s&&a.createElement("span",{className:"".concat(m,"-tags")},s)),u&&a.createElement("span",{className:"".concat(m,"-extra")},u))},ut=function(e,r){return r?a.createElement("div",{className:"".concat(e,"-footer")},r):null},ft=function(e,r){return a.createElement("div",{className:"".concat(e,"-content")},r)},vt=function(e){var r=a.useState(!1),t=(0,Pe.Z)(r,2),n=t[0],l=t[1],c=(0,it.Z)(),s=function(f){var m=f.width;c()||l(m<768)};return a.createElement(L.C,null,function(u){var f,m=u.getPrefixCls,h=u.pageHeader,d=u.direction,g,v=e.prefixCls,C=e.style,p=e.footer,x=e.children,O=e.breadcrumb,R=e.breadcrumbRender,Y=e.className,E=!0;"ghost"in e?E=e.ghost:h&&"ghost"in h&&(E=h.ghost);var N=m("page-header",v),z=function(){var ne;return((ne=O)===null||ne===void 0?void 0:ne.routes)?st(O):null},U=z(),te=O&&"props"in O,W=(g=R==null?void 0:R(e,U))!==null&&g!==void 0?g:U,se=te?O:W,he=H()(N,Y,(f={"has-breadcrumb":!!se,"has-footer":!!p},(0,B.Z)(f,"".concat(N,"-ghost"),E),(0,B.Z)(f,"".concat(N,"-rtl"),d==="rtl"),(0,B.Z)(f,"".concat(N,"-compact"),n),f));return a.createElement(_.Z,{onResize:s},a.createElement("div",{className:he,style:C},se,dt(N,e,d),x&&ft(N,x),ut(N,p)))})},mt=vt,Te=o(81253),Yt=o(18106),Be=o(90642),Me=o(64335),ht=o(21349),gt=o(85224),Vt=o(12395),Ct=o(83832),Et=function(e){if(!e)return 1;var r=e.backingStorePixelRatio||e.webkitBackingStorePixelRatio||e.mozBackingStorePixelRatio||e.msBackingStorePixelRatio||e.oBackingStorePixelRatio||e.backingStorePixelRatio||1;return(window.devicePixelRatio||1)/r},Pt=function(e){var r=e.children,t=e.style,n=e.className,l=e.markStyle,c=e.markClassName,s=e.zIndex,u=s===void 0?9:s,f=e.gapX,m=f===void 0?212:f,h=e.gapY,d=h===void 0?222:h,g=e.width,v=g===void 0?120:g,C=e.height,p=C===void 0?64:C,x=e.rotate,O=x===void 0?-22:x,R=e.image,Y=e.content,E=e.offsetLeft,N=e.offsetTop,z=e.fontStyle,U=z===void 0?"normal":z,te=e.fontWeight,W=te===void 0?"normal":te,se=e.fontColor,he=se===void 0?"rgba(0,0,0,.15)":se,Ce=e.fontSize,ne=Ce===void 0?16:Ce,De=e.fontFamily,Ae=De===void 0?"sans-serif":De,Bt=e.prefixCls,Mt=(0,a.useContext)(Ee.ZP.ConfigContext),Dt=Mt.getPrefixCls,Se=Dt("pro-layout-watermark",Bt),At=H()("".concat(Se,"-wrapper"),n),St=H()(Se,c),Lt=(0,a.useState)(""),Le=(0,Pe.Z)(Lt,2),It=Le[0],Ie=Le[1];return(0,a.useEffect)(function(){var ce=document.createElement("canvas"),J=ce.getContext("2d"),re=Et(J),Ut="".concat((m+v)*re,"px"),Wt="".concat((d+p)*re,"px"),Ft=E||m/2,wt=N||d/2;if(ce.setAttribute("width",Ut),ce.setAttribute("height",Wt),J){J.translate(Ft*re,wt*re),J.rotate(Math.PI/180*Number(O));var Ht=v*re,Ue=p*re;if(R){var de=new Image;de.crossOrigin="anonymous",de.referrerPolicy="no-referrer",de.src=R,de.onload=function(){J.drawImage(de,0,0,Ht,Ue),Ie(ce.toDataURL())}}else if(Y){var Kt=Number(ne)*re;J.font="".concat(U," normal ").concat(W," ").concat(Kt,"px/").concat(Ue,"px ").concat(Ae),J.fillStyle=he,J.fillText(Y,0,0),Ie(ce.toDataURL())}}else console.error("\u5F53\u524D\u73AF\u5883\u4E0D\u652F\u6301Canvas")},[m,d,E,N,O,U,W,v,p,Ae,he,R,Y,ne]),a.createElement("div",{style:(0,S.Z)({position:"relative"},t),className:At},r,a.createElement("div",{className:St,style:(0,S.Z)({zIndex:u,position:"absolute",left:0,top:0,width:"100%",height:"100%",backgroundSize:"".concat(m+v,"px"),pointerEvents:"none",backgroundRepeat:"repeat",backgroundImage:"url('".concat(It,"')")},l)}))},bt=Pt,yt=["title","content","pageHeaderRender","header","prefixedClassName","extraContent","style","prefixCls","breadcrumbRender"],xt=["children","loading","className","style","footer","affixProps","ghost","fixedHeader","breadcrumbRender"];function pt(i){return(0,w.Z)(i)==="object"?i:{spinning:i}}var Ot=function(e){var r=e.tabList,t=e.tabActiveKey,n=e.onTabChange,l=e.tabBarExtraContent,c=e.tabProps,s=e.prefixedClassName;return Array.isArray(r)||l?a.createElement(Be.Z,(0,P.Z)({className:"".concat(s,"-tabs"),activeKey:t,onChange:function(f){n&&n(f)},tabBarExtraContent:l},c),r==null?void 0:r.map(function(u,f){return a.createElement(Be.Z.TabPane,(0,P.Z)({},u,{tab:u.tab,key:u.key||f}))})):null},Rt=function(e,r,t){return!e&&!r?null:a.createElement("div",{className:"".concat(t,"-detail")},a.createElement("div",{className:"".concat(t,"-main")},a.createElement("div",{className:"".concat(t,"-row")},e&&a.createElement("div",{className:"".concat(t,"-content")},e),r&&a.createElement("div",{className:"".concat(t,"-extraContent")},r))))},kt=function(e){var r=useContext(RouteContext);return React.createElement("div",{style:{height:"100%",display:"flex",alignItems:"center"}},React.createElement(_Breadcrumb,_extends({},r==null?void 0:r.breadcrumb,r==null?void 0:r.breadcrumbProps,e)))},Nt=function(e){var r,t=(0,a.useContext)(Me.Z),n=e.title,l=e.content,c=e.pageHeaderRender,s=e.header,u=e.prefixedClassName,f=e.extraContent,m=e.style,h=e.prefixCls,d=e.breadcrumbRender,g=(0,Te.Z)(e,yt),v=(0,a.useMemo)(function(){if(!!d)return d},[d]);if(c===!1)return null;if(c)return a.createElement(a.Fragment,null," ",c((0,S.Z)((0,S.Z)({},e),t)));var C=n;!n&&n!==!1&&(C=t.title);var p=(0,S.Z)((0,S.Z)((0,S.Z)({},t),{},{title:C},g),{},{footer:Ot((0,S.Z)((0,S.Z)({},g),{},{breadcrumbRender:d,prefixedClassName:u}))},s),x=p.breadcrumb,O=(!x||!(x==null?void 0:x.itemRender)&&!(x==null||(r=x.routes)===null||r===void 0?void 0:r.length))&&!d;return["title","subTitle","extra","tags","footer","avatar","backIcon"].every(function(R){return!p[R]})&&O&&!l&&!f?null:a.createElement("div",{className:"".concat(u,"-warp")},a.createElement(mt,(0,P.Z)({},p,{breadcrumb:d===!1?void 0:(0,S.Z)((0,S.Z)({},p.breadcrumb),t.breadcrumbProps),breadcrumbRender:v,prefixCls:h}),(s==null?void 0:s.children)||Rt(l,f,u)))},Zt=function(e){var r,t,n=e.children,l=e.loading,c=l===void 0?!1:l,s=e.className,u=e.style,f=e.footer,m=e.affixProps,h=e.ghost,d=e.fixedHeader,g=e.breadcrumbRender,v=(0,Te.Z)(e,xt),C=(0,a.useContext)(Me.Z),p=(0,a.useContext)(Ee.ZP.ConfigContext),x=p.getPrefixCls,O=e.prefixCls||x("pro"),R="".concat(O,"-page-container"),Y=H()(R,s,(r={},(0,B.Z)(r,"".concat(O,"-page-container-ghost"),h),(0,B.Z)(r,"".concat(O,"-page-container-with-footer"),f),r)),E=(0,a.useMemo)(function(){return n?a.createElement(a.Fragment,null,a.createElement("div",{className:"".concat(R,"-children-content")},n),C.hasFooterToolbar&&a.createElement("div",{style:{height:48,marginTop:24}})):null},[n,R,C.hasFooterToolbar]),N=(0,a.useMemo)(function(){var W;return g==!1?!1:g||(v==null||(W=v.header)===null||W===void 0?void 0:W.breadcrumbRender)},[g,v==null||(t=v.header)===null||t===void 0?void 0:t.breadcrumbRender]),z=a.createElement(Nt,(0,P.Z)({},v,{breadcrumbRender:N,ghost:h,prefixCls:void 0,prefixedClassName:R})),U=(0,a.useMemo)(function(){if(a.isValidElement(c))return c;if(typeof c=="boolean"&&!c)return null;var W=pt(c);return a.createElement(Ct.Z,W)},[c]),te=(0,a.useMemo)(function(){var W=U||E;return e.waterMarkProps||C.waterMarkProps?a.createElement(bt,e.waterMarkProps||C.waterMarkProps,W):W},[e.waterMarkProps,C.waterMarkProps,U,E]);return a.createElement("div",{style:u,className:Y},d&&z?a.createElement(We,(0,P.Z)({offsetTop:C.hasHeader&&C.fixedHeader?C.headerHeight:0},m),z):z,te&&a.createElement(ht.Z,null,te),f&&a.createElement(gt.Z,{prefixCls:O},f))},Tt=Zt},56264:function(){},53645:function(){},12395:function(){},70883:function(){},81262:function(){},59903:function(){},73577:function(ae,I,o){"use strict";o.d(I,{Z:function(){return Z}});var M=o(67294);function Z(){var P=M.useRef(!0);return M.useEffect(function(){return function(){P.current=!1}},[]),function(){return!P.current}}},34952:function(ae,I,o){"use strict";var M=o(22122),Z=o(67294),P=o(15105),B=function(y,$){var w={};for(var a in y)Object.prototype.hasOwnProperty.call(y,a)&&$.indexOf(a)<0&&(w[a]=y[a]);if(y!=null&&typeof Object.getOwnPropertySymbols=="function")for(var T=0,a=Object.getOwnPropertySymbols(y);T<a.length;T++)$.indexOf(a[T])<0&&Object.prototype.propertyIsEnumerable.call(y,a[T])&&(w[a[T]]=y[a[T]]);return w},F={border:0,background:"transparent",padding:0,lineHeight:"inherit",display:"inline-block"},D=Z.forwardRef(function(y,$){var w=function(K){var G=K.keyCode;G===P.Z.ENTER&&K.preventDefault()},a=function(K){var G=K.keyCode,j=y.onClick;G===P.Z.ENTER&&j&&j()},T=y.style,H=y.noStyle,Q=y.disabled,_=B(y,["style","noStyle","disabled"]),L={};return H||(L=(0,M.Z)({},F)),Q&&(L.pointerEvents="none"),L=(0,M.Z)((0,M.Z)({},L),T),Z.createElement("div",(0,M.Z)({role:"button",tabIndex:0,ref:$},_,{onKeyDown:w,onKeyUp:a,style:L}))});I.Z=D},97435:function(ae,I){"use strict";function o(M,Z){for(var P=Object.assign({},M),B=0;B<Z.length;B+=1){var F=Z[B];delete P[F]}return P}I.Z=o}}]);
