import { Route, Routes } from 'react-router-dom'
import './App.css'
import { Products } from './pages/Products'
import { Orders } from './pages/Orders'
import { Account } from './pages/Account'
import { Register } from './pages/Register'
import { Login } from './pages/Login'
import PasswordRecovery from './pages/PasswordRecovery'
import { CreateProduct } from './pages/CreateProduct'

const App = () => {
  return (
    <>
      <Routes>
        <Route path='/' element={<Login />} />
        <Route path='/register' element={<Register />} />
        <Route path='/products' element={<Products />} />
        <Route path='/orders' element={<Orders />} />
        <Route path='/account' element={<Account />} />
        <Route path='/forgot_password' element={<PasswordRecovery />} />
        <Route path='/create_product' element={<CreateProduct />} />
      </Routes>
    </>
  )
}

export default App
