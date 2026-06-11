import { useState, useEffect } from "react";

const style = `
  @import url('https://fonts.googleapis.com/css2?family=Fraunces:ital,wght@0,300;0,700;0,900;1,300&family=Cabinet+Grotesk:wght@400;500;600;700&display=swap');

  *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

  body {
    font-family: 'Cabinet Grotesk', sans-serif;
    background: linear-gradient(135deg, #f0f7ff 0%, #dbeafe 50%, #bfdbfe 100%);
    color: #0f172a;
  }

  @keyframes fadeUp {
    from { opacity: 0; transform: translateY(32px); }
    to   { opacity: 1; transform: translateY(0); }
  }
  @keyframes floatA {
    0%, 100% { transform: translateY(0px); }
    50%       { transform: translateY(-18px); }
  }
  @keyframes floatB {
    0%, 100% { transform: translateY(0px); }
    50%       { transform: translateY(14px); }
  }
  @keyframes shimmer {
    0%   { background-position: -200% center; }
    100% { background-position: 200% center; }
  }
  @keyframes pulse {
    0%, 100% { transform: scale(1); opacity: 1; }
    50%       { transform: scale(1.04); opacity: 0.85; }
  }

  .fade1 { animation: fadeUp 0.7s 0.1s ease both; }
  .fade2 { animation: fadeUp 0.7s 0.25s ease both; }
  .fade3 { animation: fadeUp 0.7s 0.4s ease both; }
  .fade4 { animation: fadeUp 0.7s 0.55s ease both; }
`;

const FloatingCard = ({ icon, label, value, style: s }) => (
  <div style={{
    position: "absolute", ...s,
    background: "rgba(255,255,255,0.92)",
    backdropFilter: "blur(12px)",
    borderRadius: 16,
    padding: "14px 18px",
    boxShadow: "0 8px 32px rgba(26,95,212,0.13)",
    display: "flex", alignItems: "center", gap: 12,
    border: "1px solid rgba(26,95,212,0.1)",
    minWidth: 160, zIndex: 3,
  }}>
    <span style={{ fontSize: 28 }}>{icon}</span>
    <div>
      <div style={{ fontWeight: 700, fontSize: 18, color: "#0f172a", lineHeight: 1.1 }}>{value}</div>
      <div style={{ fontSize: 12, color: "#64748b", fontWeight: 500 }}>{label}</div>
    </div>
  </div>
);

export default function LandingPage({ onNavigate }) {
  const [scrolled, setScrolled] = useState(false);
  const [hovered, setHovered] = useState(false);

  useEffect(() => {
    const onScroll = () => setScrolled(window.scrollY > 20);
    window.addEventListener("scroll", onScroll);
    return () => window.removeEventListener("scroll", onScroll);
  }, []);

  return (
    <>
      <style>{style}</style>

      {/* Navbar */}
      <nav style={{
        position: "fixed", top: 0, left: 0, right: 0, zIndex: 100,
        padding: "0 48px", height: 68,
        display: "flex", alignItems: "center", justifyContent: "space-between",
        background: scrolled ? "rgba(240,247,255,0.9)" : "transparent",
        backdropFilter: scrolled ? "blur(16px)" : "none",
        borderBottom: scrolled ? "1px solid rgba(26,95,212,0.08)" : "none",
        transition: "all 0.3s ease",
      }}>
        {/* Logo */}
        <div style={{ display: "flex", alignItems: "center", gap: 10 }}>
          <div style={{
            width: 36, height: 36, borderRadius: 10,
            background: "linear-gradient(135deg, #1a5fd4, #38bdf8)",
            display: "flex", alignItems: "center", justifyContent: "center", fontSize: 18,
          }}>🏥</div>
          <span style={{
            fontFamily: "'Fraunces', serif", fontWeight: 700, fontSize: 22,
            background: "linear-gradient(90deg, #1a5fd4, #38bdf8)",
            WebkitBackgroundClip: "text", WebkitTextFillColor: "transparent",
          }}>Sante237</span>
        </div>

        {/* Liens + boutons */}
        <div style={{ display: "flex", alignItems: "center", gap: 24 }}>
       
          <button onClick={() => onNavigate?.("register")} style={{
            padding: "9px 22px", borderRadius: 99,
            background: "transparent",
            color: "#1a5fd4", border: "2px solid rgba(26,95,212,0.3)",fontSize: 14, fontWeight: 600, cursor: "pointer",
          }}>
            Inscription
          </button>
          <button onClick={() => onNavigate?.("login")} style={{
            padding: "9px 22px", borderRadius: 99,
            background: "linear-gradient(135deg, #1a5fd4, #0d3d8f)",
            color: "white", border: "none", fontSize: 14, fontWeight: 600, cursor: "pointer",
            boxShadow: "0 4px 14px rgba(26,95,212,0.35)",
          }}>
            Connexion
          </button>
        </div>
      </nav>

      {/* Hero */}
      <section style={{
        minHeight: "100vh", display: "flex", alignItems: "center",
        padding: "100px 48px 60px", position: "relative", overflow: "hidden",
        background: "linear-gradient(135deg, #f0f7ff 0%, #dbeafe 50%, #bfdbfe 100%)",
      }}>
        <div style={{
          position: "absolute", inset: 0, zIndex: 0,
          backgroundImage: "radial-gradient(circle, rgba(26,95,212,0.07) 1px, transparent 1px)",
          backgroundSize: "32px 32px",
        }} />

        {/* Texte */}
        <div style={{ flex: 1, maxWidth: 580, position: "relative", zIndex: 2 }}>
          <div className="fade1" style={{
            display: "inline-flex", alignItems: "center", gap: 8,
            background: "rgba(26,95,212,0.08)", border: "1px solid rgba(26,95,212,0.15)",
            borderRadius: 99, padding: "6px 14px", marginBottom: 28,
          }}>
            <span style={{ width: 8, height: 8, borderRadius: "50%", background: "#10b981", animation: "pulse 2s infinite", display: "inline-block" }} />
            <span style={{ fontSize: 13, color: "#1a5fd4", fontWeight: 600 }}>Plateforme médicale au Cameroun</span>
          </div>

          <h1 className="fade2" style={{
            fontFamily: "'Fraunces', serif",
            fontSize: "clamp(42px, 5.5vw, 70px)",
            fontWeight: 900, lineHeight: 1.05, color: "#0f172a", marginBottom: 24,
          }}>
            Votre santé,<br />
            <span style={{
              background: "linear-gradient(90deg, #1a5fd4 0%, #38bdf8 50%, #1a5fd4 100%)",
              backgroundSize: "200% auto",
              WebkitBackgroundClip: "text", WebkitTextFillColor: "transparent",
              animation: "shimmer 4s linear infinite",
            }}>simplifiée.</span>
          </h1>

          <p className="fade3" style={{ fontSize: 18, color: "#475569", lineHeight: 1.7, marginBottom: 40, maxWidth: 460 }}>
            Prenez rendez-vous avec les meilleurs médecins du Cameroun en quelques clics. Rapide, sécurisé et disponible 24h/24.
          </p>

          <div className="fade4" style={{ display: "flex", gap: 14 }}>
            <button onClick={() => onNavigate?.("login")}
              onMouseEnter={() => setHovered(true)}
              onMouseLeave={() => setHovered(false)}
              style={{
                padding: "15px 36px", borderRadius: 99,
                background: "linear-gradient(135deg, #1a5fd4, #0d3d8f)",
                color: "white", border: "none", fontSize: 16, fontWeight: 700, cursor: "pointer",
                boxShadow: hovered ? "0 10px 30px rgba(26,95,212,0.5)" : "0 6px 20px rgba(26,95,212,0.3)",
                transform: hovered ? "translateY(-2px)" : "none",
                transition: "all .25s ease",
              }}>
              Prendre un rendez-vous →
            </button>
          </div>

          {/* Stats */}
          <div className="fade4" style={{ display: "flex", gap: 36, marginTop: 52 }}>
            {[{ val: "500+", label: "Médecins" }, { val: "12k+", label: "Patients" }, { val: "98%", label: "Satisfaction" }].map(s => (
              <div key={s.label}>
                <div style={{ fontFamily: "'Fraunces', serif", fontSize: 30, fontWeight: 700, color: "#1a5fd4" }}>{s.val}</div>
                <div style={{ fontSize: 13, color: "#94a3b8", fontWeight: 500 }}>{s.label}</div>
              </div>
            ))}
          </div>
        </div>

        {/* Illustration */}<div style={{ flex: 1, position: "relative", height: 520, display: "flex", alignItems: "center", justifyContent: "center" }}>
          <div style={{
            width: 320, height: 320, borderRadius: "50%",
            background: "linear-gradient(135deg, rgba(26,95,212,0.08), rgba(56,189,248,0.12))",
            border: "2px solid rgba(26,95,212,0.1)",
            display: "flex", alignItems: "center", justifyContent: "center",
            fontSize: 100, animation: "floatA 6s ease-in-out infinite", zIndex: 2,
          }}>🏥</div>

          <FloatingCard icon="👨‍⚕️" label="Médecins disponibles" value="500+"
            style={{ top: "8%", left: "5%", animation: "floatB 7s ease-in-out infinite" }} />
          <FloatingCard icon="📅" label="RDV aujourd'hui" value="124"
            style={{ bottom: "18%", left: "2%", animation: "floatA 8s ease-in-out infinite" }} />
          <FloatingCard icon="⭐" label="Note moyenne" value="4.8/5"
            style={{ top: "20%", right: "4%", animation: "floatB 9s ease-in-out infinite" }} />
          <FloatingCard icon="🔔" label="Rappels automatiques" value="SMS & Email"
            style={{ bottom: "10%", right: "8%", animation: "floatA 6s ease-in-out infinite" }} />
        </div>
      </section>

      {/* Footer */}
      <footer style={{
        textAlign: "center", padding: "28px 48px",
        borderTop: "1px solid rgba(26,95,212,0.08)",
        color: "#64748b", fontSize: 13,
        background: "rgba(255,255,255,0.5)",
      }}>
        © 2026 <strong style={{ color: "#1a5fd4" }}>Sante237</strong> — Plateforme médicale du Cameroun
      </footer>
    </>
  );
}