(self.webpackChunkant_design_pro=self.webpackChunkant_design_pro||[]).push([[7567],{70499:function(x){x.exports={activeChart:"activeChart___15gpK",activeChartGrid:"activeChartGrid___3PL4c",activeChartLegend:"activeChartLegend___2Ieyc",dashedLine:"dashedLine___2fgZX",line:"line___2cxqk"}},19134:function(x){x.exports={mapChart:"mapChart___4fQJB",pieCard:"pieCard___1ETZj"}},6866:function(x,B,e){"use strict";e.r(B),e.d(B,{default:function(){return k}});var q=e(58024),h=e(39144),_=e(13062),g=e(71230),tt=e(89032),u=e(15746),et=e(95300),m=e(7277),F=e(21010),d=e(72178),G=e(21349),R=e(92077),E=e.n(R),W=e(2824),A=e(3182),M=e(69610),S=e(54941),T=e(81306),N=e(59206),H=e(94043),j=e.n(H),L=e(67294),p=e(77041),J=e(83832),t=e(85893),K=["#eff3ff","#c6dbef","#9ecae1","#6baed6","#4292c6","#2171b5","#084594"],Q=function(o){(0,T.Z)(s,o);var l=(0,N.Z)(s);function s(){var n;(0,M.Z)(this,s);for(var i=arguments.length,a=new Array(i),r=0;r<i;r++)a[r]=arguments[r];return n=l.call.apply(l,[this].concat(a)),n.state={data:null,grid:null,loading:!1},n}return(0,S.Z)(s,[{key:"componentDidMount",value:function(){var n=(0,A.Z)(j().mark(function a(){var r,v,C,y;return j().wrap(function(f){for(;;)switch(f.prev=f.next){case 0:return f.next=2,Promise.all([fetch("https://gw.alipayobjects.com/os/bmw-prod/c5dba875-b6ea-4e88-b778-66a862906c93.json").then(function(D){return D.json()}),fetch("https://gw.alipayobjects.com/os/bmw-prod/8990e8b4-c58e-419b-afb9-8ea3daff2dd1.json").then(function(D){return D.json()})]);case 2:r=f.sent,v=(0,W.Z)(r,2),C=v[0],y=v[1],this.setState({data:C,grid:y,loading:!0});case 7:case"end":return f.stop()}},a,this)}));function i(){return n.apply(this,arguments)}return i}()},{key:"render",value:function(){var i=this.state,a=i.data,r=i.grid,v=i.loading;return v===!1?(0,t.jsx)(J.Z,{}):(0,t.jsxs)(p.JE,{map:{center:[110.19382669582967,50.258134],pitch:0,style:"blank",zoom:1},style:{position:"relative",width:"100%",height:"452px"},children:[r&&(0,t.jsx)(p.zP,{source:{data:r,transforms:[{type:"hexagon",size:8e5,field:"capacity",method:"sum"}]},color:{values:"#ddd"},shape:{values:"hexagon"},style:{coverage:.7,opacity:.8}},"1"),a&&[(0,t.jsx)(p.ns,{options:{autoFit:!0},source:{data:a},scale:{values:{color:{field:"cum_conf",type:"quantile"},size:{field:"cum_conf",type:"log"}}},color:{field:"cum_conf",values:K},shape:{values:"circle"},active:{option:{color:"#0c2c84"}},size:{field:"cum_conf",values:[0,30]},style:{opacity:.8}},"2"),(0,t.jsx)(p.ns,{source:{data:a},color:{values:"#fff"},shape:{field:"Short_Name_ZH",values:"text"},filter:{field:"cum_conf",values:function(y){return y>2e3}},size:{values:12},style:{opacity:1,strokeOpacity:1,strokeWidth:0}},"5")]]})}}]),s}(L.Component),$=e(86582),U=e(70499),c=e.n(U);function I(o){return o*1<10?"0".concat(o):o}function z(){for(var o=[],l=0;l<24;l+=1)o.push({x:"".concat(I(l),":00"),y:Math.floor(Math.random()*200)+l*50});return o}var O=function(o){(0,T.Z)(s,o);var l=(0,N.Z)(s);function s(){var n;(0,M.Z)(this,s);for(var i=arguments.length,a=new Array(i),r=0;r<i;r++)a[r]=arguments[r];return n=l.call.apply(l,[this].concat(a)),n.state={activeData:z()},n.timer=void 0,n.requestRef=void 0,n.loopData=function(){n.requestRef=requestAnimationFrame(function(){n.timer=window.setTimeout(function(){n.setState({activeData:z()},function(){n.loopData()})},1e3)})},n}return(0,S.Z)(s,[{key:"componentDidMount",value:function(){this.loopData()}},{key:"componentWillUnmount",value:function(){clearTimeout(this.timer),this.requestRef&&cancelAnimationFrame(this.requestRef)}},{key:"render",value:function(){var i=this.state.activeData,a=i===void 0?[]:i;return(0,t.jsxs)("div",{className:c().activeChart,children:[(0,t.jsx)(m.Z,{title:"\u76EE\u6807\u8BC4\u4F30",value:"\u6709\u671B\u8FBE\u5230\u9884\u671F"}),(0,t.jsx)("div",{style:{marginTop:32},children:(0,t.jsx)(d.m,{data:a,xField:"x",forceFit:!0,yField:"y",height:84})}),a&&(0,t.jsxs)("div",{children:[(0,t.jsxs)("div",{className:c().activeChartGrid,children:[(0,t.jsxs)("p",{children:[(0,$.Z)(a).sort()[a.length-1].y+200," \u4EBF\u5143"]}),(0,t.jsxs)("p",{children:[(0,$.Z)(a).sort()[Math.floor(a.length/2)].y," \u4EBF\u5143"]})]}),(0,t.jsx)("div",{className:c().dashedLine,children:(0,t.jsx)("div",{className:c().line})}),(0,t.jsx)("div",{className:c().dashedLine,children:(0,t.jsx)("div",{className:c().line})})]}),a&&(0,t.jsxs)("div",{className:c().activeChartLegend,children:[(0,t.jsx)("span",{children:"00:00"}),(0,t.jsx)("span",{children:a[Math.floor(a.length/2)].x}),(0,t.jsx)("span",{children:a[a.length-1].x})]})]})}}]),s}(L.Component);function X(){return Z.apply(this,arguments)}function Z(){return Z=(0,A.Z)(j().mark(function o(){return j().wrap(function(s){for(;;)switch(s.prev=s.next){case 0:return s.abrupt("return",(0,F.WY)("/api/tags"));case 1:case"end":return s.stop()}},o)})),Z.apply(this,arguments)}var Y=e(19134),P=e.n(Y),V=m.Z.Countdown,b=Date.now()+1e3*60*60*24*2+1e3*30,w=function(){var l=(0,F.QT)(X),s=l.loading,n=l.data,i=((n==null?void 0:n.list)||[]).map(function(a){return{id:+Date.now(),word:a.name,weight:a.value}});return(0,t.jsx)(G.Z,{children:(0,t.jsxs)(t.Fragment,{children:[(0,t.jsxs)(g.Z,{gutter:24,children:[(0,t.jsx)(u.Z,{xl:18,lg:24,md:24,sm:24,xs:24,style:{marginBottom:24},children:(0,t.jsxs)(h.Z,{title:"\u6D3B\u52A8\u5B9E\u65F6\u4EA4\u6613\u60C5\u51B5",bordered:!1,children:[(0,t.jsxs)(g.Z,{children:[(0,t.jsx)(u.Z,{md:6,sm:12,xs:24,children:(0,t.jsx)(m.Z,{title:"\u4ECA\u65E5\u4EA4\u6613\u603B\u989D",suffix:"\u5143",value:E()(124543233).format("0,0")})}),(0,t.jsx)(u.Z,{md:6,sm:12,xs:24,children:(0,t.jsx)(m.Z,{title:"\u9500\u552E\u76EE\u6807\u5B8C\u6210\u7387",value:"92%"})}),(0,t.jsx)(u.Z,{md:6,sm:12,xs:24,children:(0,t.jsx)(V,{title:"\u6D3B\u52A8\u5269\u4F59\u65F6\u95F4",value:b,format:"HH:mm:ss:SSS"})}),(0,t.jsx)(u.Z,{md:6,sm:12,xs:24,children:(0,t.jsx)(m.Z,{title:"\u6BCF\u79D2\u4EA4\u6613\u603B\u989D",suffix:"\u5143",value:E()(234).format("0,0")})})]}),(0,t.jsx)("div",{className:P().mapChart,children:(0,t.jsx)(Q,{})})]})}),(0,t.jsxs)(u.Z,{xl:6,lg:24,md:24,sm:24,xs:24,children:[(0,t.jsx)(h.Z,{title:"\u6D3B\u52A8\u60C5\u51B5\u9884\u6D4B",style:{marginBottom:24},bordered:!1,children:(0,t.jsx)(O,{})}),(0,t.jsx)(h.Z,{title:"\u5238\u6838\u6548\u7387",style:{marginBottom:24},bodyStyle:{textAlign:"center"},bordered:!1,children:(0,t.jsx)(d.aC,{height:180,min:0,max:100,forceFit:!0,value:87,range:[0,25,50,75,100],statistic:{visible:!0,text:"\u4F18",color:"#30bf78"}})})]})]}),(0,t.jsxs)(g.Z,{gutter:24,children:[(0,t.jsx)(u.Z,{xl:12,lg:24,sm:24,xs:24,style:{marginBottom:24},children:(0,t.jsx)(h.Z,{title:"\u5404\u54C1\u7C7B\u5360\u6BD4",bordered:!1,className:P().pieCard,children:(0,t.jsxs)(g.Z,{style:{padding:"16px 0"},children:[(0,t.jsx)(u.Z,{span:8,children:(0,t.jsx)(d.TZ,{forceFit:!0,height:128,percent:.28})}),(0,t.jsx)(u.Z,{span:8,children:(0,t.jsx)(d.TZ,{color:"#5DDECF",forceFit:!0,height:128,percent:.22})}),(0,t.jsx)(u.Z,{span:8,children:(0,t.jsx)(d.TZ,{color:"#2FC25B",forceFit:!0,height:128,percent:.32})})]})})}),(0,t.jsx)(u.Z,{xl:6,lg:12,sm:24,xs:24,style:{marginBottom:24},children:(0,t.jsx)(h.Z,{title:"\u70ED\u95E8\u641C\u7D22",loading:s,bordered:!1,bodyStyle:{overflow:"hidden"},children:(0,t.jsx)(d.kB,{data:i,forceFit:!0,height:162,wordStyle:{fontSize:[10,20]},shape:"triangle"})})}),(0,t.jsx)(u.Z,{xl:6,lg:12,sm:24,xs:24,style:{marginBottom:24},children:(0,t.jsx)(h.Z,{title:"\u8D44\u6E90\u5269\u4F59",bodyStyle:{textAlign:"center",fontSize:0},bordered:!1,children:(0,t.jsx)(d.Kj,{height:161,min:0,max:1e4,value:5639,forceFit:!0,padding:[0,0,0,0],statistic:{formatter:function(r){return"".concat((100*r/1e4).toFixed(1),"%")}}})})})]})]})})},k=w}}]);
