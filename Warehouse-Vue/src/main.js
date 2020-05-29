import Vue from 'vue'
import axios from 'axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import App from '@/App.vue'
import store from '@/store/index.js'
import router from '@/router/index.js'

axios.defaults.baseURL = '/api/';
axios.defaults.withCredentials = true;
Vue.prototype.$axios = axios;
Vue.config.productionTip = false;
Vue.use(ElementUI);

router.beforeEach((to, from, next) => {
    // 设置标题
    document.title = to.name ? to.name : "仓库信息管理系统";
    if (to.meta.requireAuth) {
      if (store.state.currentUser) {
        next();
      } else {
    		next('/login');
      }
    } else {
    		next();
    }
});

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App),
});
