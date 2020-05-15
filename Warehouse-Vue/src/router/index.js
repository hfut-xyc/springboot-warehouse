import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from "@/views/Login.vue"
import Home from '@/views/Home.vue'
import Warehouse from '@/components/warehouse/Warehouse.vue'
import Inventory from '@/components/product/Inventory.vue'
import Order from '@/components/product/Order.vue'
import Chart from '@/components/product/Chart.vue'
import User from '@/components/human/User.vue'
import Employee from '@/components/human/Employee.vue'
import WarehouseDetail from "@/components/warehouse/WarehouseDetail";

Vue.use(VueRouter);

export default new VueRouter({
	mode: 'history',
	routes: [
		{
			path: '/',
			redirect: '/login',
		},
		{
			path: '/login',
			name: '用户登录',
			component: Login
		},
		{
			path: '/home',
			component: Home,
			meta: { requireAuth: true },
			children: [
				{
					path: '',
					redirect: 'warehouse'
				},
				{
					path: 'warehouse',
					name: "仓库信息",
					component: Warehouse,
					meta: { requireAuth: true }
				},
				{
					path: 'warehouse/:id/detail',
					name: "仓库详情",
					component: WarehouseDetail,
					meta: { requireAuth: true}
				},
				{
					path: 'inventory',
					name: "产品清单",
					component: Inventory,
					meta: { requireAuth: true }
				},
				{
					path: 'stock-io',
					name: "订单管理",
					component: Order,
					meta: { requireAuth: true }
				},
				{
					path: 'employee',
					name: "员工管理",
					component: Employee,
					meta: { requireAuth: true }
				},
				{
					path: 'user',
					name: "用户管理",
					component: User,
					meta: { requireAuth: true }
				},
				{
					path: 'chart',
					name: "数据统计",
					component: Chart,
					meta: { requireAuth: true }
				}
			]
		}
	]
});