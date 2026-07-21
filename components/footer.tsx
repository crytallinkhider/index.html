import { Zap } from "lucide-react"

const columns = [
  {
    title: "Produto",
    links: ["Recursos", "Planos", "Automação", "Atualizações"],
  },
  {
    title: "Empresa",
    links: ["Sobre", "Blog", "Contato", "Parcerias"],
  },
  {
    title: "Suporte",
    links: ["Central de ajuda", "Discord", "Status", "Termos"],
  },
]

export function Footer() {
  return (
    <footer className="relative border-t border-border px-4 py-14">
      <div className="mx-auto max-w-6xl">
        <div className="grid gap-10 md:grid-cols-[1.5fr_repeat(3,1fr)]">
          <div>
            <div className="flex items-center gap-2">
              <span className="flex h-7 w-7 items-center justify-center rounded-full bg-primary text-primary-foreground">
                <Zap className="h-4 w-4" />
              </span>
              <span className="text-sm font-semibold tracking-tight">Immortal</span>
            </div>
            <p className="mt-4 max-w-xs text-sm leading-relaxed text-muted-foreground">
              A plataforma definitiva para bypass, automação e gestão de contas Roblox.
            </p>
          </div>

          {columns.map((column) => (
            <div key={column.title}>
              <h3 className="text-sm font-semibold">{column.title}</h3>
              <ul className="mt-4 flex flex-col gap-2.5">
                {column.links.map((link) => (
                  <li key={link}>
                    <a
                      href="#"
                      className="text-sm text-muted-foreground transition-colors hover:text-foreground"
                    >
                      {link}
                    </a>
                  </li>
                ))}
              </ul>
            </div>
          ))}
        </div>

        <div className="mt-12 flex flex-col items-center justify-between gap-3 border-t border-border pt-6 text-sm text-muted-foreground sm:flex-row">
          <p>© {new Date().getFullYear()} Immortal Dashboard. Todos os direitos reservados.</p>
          <p>Não afiliado à Roblox Corporation.</p>
        </div>
      </div>
    </footer>
  )
}
