import { useState } from 'react'
import { helpHttp } from '../helpers/helpHttp'
import Loader from './Loader'

export const OrderCard = ({ userName, userLastName, orderState, products }) => {
  const [loading, setLoading] = useState(false)

  const handleToggleState = () => {
    setLoading(true)
    helpHttp().put(
      'endpint cambiar estado',
      {
        headers: {
          'Content-Type': 'Application/json',
          Accept: 'application/json'
        }
      })
      .then(res => {
        setLoading(false)
      })
    return () => {
    }
  }

  return (
    <>
      <article className='product-card'>
        <h4>{userName}- {userLastName}</h4>
        <p>Estado: {orderState}</p>
        {window.sessionStorage.getItem('admin')
          ? loading
            ? <Loader />
            : <button onClick={handleToggleState}>Cambiar Estado</button>
          : <></>}
      </article>
      <ul className='porperties-list'>
        {products.map((product) => (
          <li key={product.id}>{product.name} - {product.amount}</li>
        ))}
      </ul>
    </>
  )
}
