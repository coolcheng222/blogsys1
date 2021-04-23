import {createRouter, createWebHashHistory} from 'vue-router'
import User from "../components/user/User";
import Register from "../components/user/Register";
import Login from "../components/user/Login";
import index from "../components/index/index"

const routes = [
    {
        path: '/user',
        name: 'user',
        component: User,
        children: [
            {
                path: 'register',
                name: 'register',
                component: Register,
                alias: '/register'
            },
            {
                path: 'login',
                name: 'login',
                component: Login,
                alias: '/login'
            }
        ]
    },
    {name:'index',path:'/index',component: index},
    {name:'default',path:"/",redirect:'/index'}
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
