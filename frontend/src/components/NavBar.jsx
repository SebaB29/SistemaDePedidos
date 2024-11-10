import { useNavigate } from 'react-router-dom'

export const NavBar = () => {
  const navigate = useNavigate()
  return (
    <nav>
      <ul className='header-buttons'>
        <li><button onClick={() => navigate('/products')}>Productos</button></li>
        <li><button onClick={() => navigate('/orders')}>Ordenes</button></li>
        <li><button onClick={() => navigate('/account')}>Mi Usuario</button></li>
      </ul>
    </nav>
  )
}
