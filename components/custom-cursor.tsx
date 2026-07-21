"use client"

import { useEffect, useRef } from "react"

export function CustomCursor() {
  const cursorRef = useRef<HTMLDivElement>(null)

  useEffect(() => {
    const cursor = cursorRef.current
    if (!cursor) return

    let mouseX = 0
    let mouseY = 0
    let raf = 0

    const render = () => {
      cursor.style.left = `${mouseX}px`
      cursor.style.top = `${mouseY}px`
      raf = requestAnimationFrame(render)
    }

    const onMove = (e: MouseEvent) => {
      mouseX = e.clientX
      mouseY = e.clientY
    }
    const onDown = () => cursor.classList.add("clicked")
    const onUp = () => cursor.classList.remove("clicked")

    const hoverTargets = ["a", "button", "input", "textarea", "select"]
    const onOver = (e: MouseEvent) => {
      const target = e.target as Element
      if (hoverTargets.some((s) => target.closest(s))) cursor.classList.add("hover")
    }
    const onOut = (e: MouseEvent) => {
      const target = e.target as Element
      if (hoverTargets.some((s) => target.closest(s))) cursor.classList.remove("hover")
    }

    window.addEventListener("mousemove", onMove)
    window.addEventListener("mousedown", onDown)
    window.addEventListener("mouseup", onUp)
    window.addEventListener("mouseover", onOver)
    window.addEventListener("mouseout", onOut)
    raf = requestAnimationFrame(render)

    return () => {
      window.removeEventListener("mousemove", onMove)
      window.removeEventListener("mousedown", onDown)
      window.removeEventListener("mouseup", onUp)
      window.removeEventListener("mouseover", onOver)
      window.removeEventListener("mouseout", onOut)
      cancelAnimationFrame(raf)
    }
  }, [])

  return <div ref={cursorRef} className="custom-cursor" aria-hidden="true" />
}
