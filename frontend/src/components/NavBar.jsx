import { useNavigate } from 'react-router-dom'

export const NavBar = () => {
  const navigate = useNavigate()

  const handleLogOut = () => {
    window.sessionStorage.clear()
    navigate('/')
  }

  return (
    <nav>
      <ul className='header-buttons'>
        <li><button onClick={() => navigate('/products')}>Productos</button></li>
        <li><button onClick={() => navigate('/orders')}>Ordenes</button></li>
        <li><button onClick={() => navigate('/account')}>Mi Usuario</button></li>
        <li><button onClick={handleLogOut}>Cerrar sesion</button></li>
      </ul>
    </nav>
  )
}
