import { useEffect, useState } from 'react'
import Header from '../components/Header'
import { Main } from '../components/Main'
import Loader from '../components/Loader'
import { ProductCard } from '../components/ProductCard'
import { useNavigate } from 'react-router-dom'
import { helpHttp } from '../helpers/helpHttp'
import Message from '../components/Message'
import { Cart } from '../components/Cart'

const ENDPOINT = 'http://localhost:8080/product'

export const Products = () => {
  const [loading, setLoading] = useState(false)
  const [response, setResponse] = useState({})
  const [cart, setCart] = useState([0])
  const [carts, setCarts] = useState([cart])
  const navigate = useNavigate()

  const handleSelect = (e, cart) => {
    e.stopPropagation()
    if (e.target.classList.contains('cart-button')) return
    setCart(cart)
  }

  useEffect(() => {
    const newCarts = carts.map(el => {
      if (el[0] === cart[0]) return cart
      return el
    })
    setCarts(newCarts)
  }, [cart])

  useEffect(() => {
    setLoading(true)
    helpHttp().get(
      ENDPOINT,
      {
        headers: {
          'Content-Type': 'Application/json',
          Accept: 'application/json'
        }
      })
      .then(async res => {
        if (res.status !== 'OK') throw res
        let newData = []
        for (let i = 0; i < res.data.length; i++) {
          const product = res.data[i]
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
          newData = [...newData, { ...product, ...stock.data }]
        }
        setLoading(false)
        setResponse({ ...res, data: newData })
      })
      .catch(err => {
        setLoading(false)
        setResponse({
          status: '500',
          error: err,
          data: null
        })
      })
  }, [])

  return (
    <>
      <Header title='Productos' />
      <Main>
        <div className='barra-superior-products'>
          {carts.map((el, index) => (
            <Cart myOnClick={handleSelect} key={index} thisCart={el} cart={cart} setCart={setCart} carts={carts} setCarts={setCarts} />
          ))}
          <button onClick={e => setCarts([...carts, [carts.at(-1)[0] + 1]])}>Agregar Carrito</button>
        </div>
        {window.sessionStorage.getItem('rol') === 'ADMIN' &&
          <><button onClick={() => navigate('/create_product')}>Agregar Producto</button><hr /></>}
        {loading && <Loader />}
        {response.status && response.status === 'OK'
          ? response.data.map((product, index) => (
            <ProductCard
              key={index}
              product={product}
              cart={cart}
              setCart={setCart}
            />
          ))
          : !loading && <Message bgColor='#ff0000' message={response.status + ': ' + response.error} />}
      </Main>
    </>
  )
}
