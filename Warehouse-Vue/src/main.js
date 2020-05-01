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

// router.beforeEach((to, from, next) => {
//     if (to.meta.requireAuth) {
//       if (store.state.username) {
//         next();
//       } else {
// 				next({path: '/login'});
//       }
//     } else {
// 			if (store.state.username && to.path.startsWith("/login")) {
// 			  next({path: '/home'});
// 			} else {
// 				next();
// 			}
//     }
// })

new Vue({
	el: '#app',
  router,
	store,
  render: h => h(App),
})
