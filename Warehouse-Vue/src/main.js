import Vue from 'vue'
import axios from 'axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import App from '@/App.vue'
import store from '@/store/index.js'
import router from '@/router/index.js'

axios.defaults.baseURL = 'http://localhost:8081'
axios.defaults.withCredentials = true
Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(ElementUI)

router.beforeEach((to, from, next) => {
  // console.log({"to": to, "from": from, "next": next});
  // 设置标题
  if (to.name) {
    document.title = to.name;
  }
  else {
    document.title = "仓库信息管理系统";
  }
  next();
  // 这里应该要用Session向后端来验证是不是登陆了
  // 如果已经修改好了，记得把上面的next()去掉
  // 另外，我建议路径 / 在已经登陆时指向 /home
  // if (to.meta.requireAuth) {
  //   if (store.state.username) {
  //     next();
  //   } else {
  // 		next({path: '/login'});
  //   }
  // } else {
  // 	if (store.state.username && to.path.startsWith("/login")) {
  // 	  next({path: '/home'});
  // 	} else {
  // 		next();
  // 	}
  // }
})

new Vue({
	el: '#app',
  router,
	store,
  render: h => h(App),
})
