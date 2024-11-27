import { useEffect, useState } from 'react'
import { helpHttp } from '../helpers/helpHttp'
import Loader from './Loader'

export const OrderCard = ({ order, numeroDeOrden }) => {
  const [loading, setLoading] = useState(false)
  const [products, setProducts] = useState(order.product_list)

  const fetchStocks = async () => {
    let newProducts = []
    for (let i = 0; i < products.length; i++) {
      const product = products[i]
      const stockRes = await fetch(
        `http://localhost:8080/product/${product.productId}/stock`,
        {
          headers: {
            Accept: 'application/json',
            Authorization: `Bearer ${window.sessionStorage.getItem('access_token')}`
          }
        }
      )
      const stock = await stockRes.json()
      newProducts = [...newProducts, { ...product, ...stock.data }]
    }
    setLoading(false)
    setProducts(newProducts)
  }

  useEffect(() => {
    fetchStocks()
  }, [])

  const handleToggleState = () => {
    setLoading(true)
    helpHttp().put(
      'http://localhost:8080/order/state',
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
        <h4>Orden {numeroDeOrden}</h4>
        <p>Estado: {order.order_state}</p>
        <p>Fecha de creacion: {order.creation_date}</p>
        {window.sessionStorage.getItem('rol') === 'ADMIN' &&
          loading
          ? <Loader />
          : <button onClick={handleToggleState}>Cambiar Estado</button>}
      </article>
      <ul className='porperties-list'>
        {products.map((product, index) => (
          <li key={index}>{product.name} - {product.quantity}</li>
        ))}
      </ul>
    </>
  )
}
