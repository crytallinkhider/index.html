import type { Metadata, Viewport } from "next"
import { Geist, Geist_Mono } from "next/font/google"
import { CustomCursor } from "@/components/custom-cursor"
import "./globals.css"

const geistSans = Geist({ subsets: ["latin"], variable: "--font-geist-sans" })
const geistMono = Geist_Mono({ subsets: ["latin"], variable: "--font-geist-mono" })

export const metadata: Metadata = {
  title: "Immortal Dashboard",
  description: "A plataforma definitiva para bypass, automação e gestão de contas Roblox.",
  authors: [{ name: "Immortal" }],
  openGraph: {
    title: "Immortal Dashboard",
    description: "A plataforma definitiva para bypass, automação e gestão de contas Roblox.",
    type: "website",
  },
  twitter: {
    card: "summary_large_image",
    site: "@immortal",
  },
}

export const viewport: Viewport = {
  themeColor: "#01030b",
  width: "device-width",
  initialScale: 1,
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="pt-BR" className={`${geistSans.variable} ${geistMono.variable} bg-background`}>
      <body className="font-sans antialiased">
        {children}
        <CustomCursor />
      </body>
    </html>
  )
}
