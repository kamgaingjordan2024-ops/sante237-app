import { useState } from "react";

const API = "http://localhost:8081";

const style = `
  @import url('https://fonts.googleapis.com/css2?family=Fraunces:ital,wght@0,300;0,700;0,900;1,300&family=Cabinet+Grotesk:wght@400;500;600;700&display=swap');
  *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
  body { font-family: 'Cabinet Grotesk', sans-serif; min-height: 100vh; }
  @keyframes fadeUp {
    from { opacity: 0; transform: translateY(24px); }
    to   { opacity: 1; transform: translateY(0); }
  }
  @keyframes spin { to { transform: rotate(360deg); } }
  .fade1 { animation: fadeUp 0.5s 0.05s ease both; }
  .fade2 { animation: fadeUp 0.5s 0.15s ease both; }
  .fade4 { animation: fadeUp 0.5s 0.35s ease both; }
  input:focus { outline: none; }
  button:focus { outline: none; }
`;

const InputField = ({ label, type = "text", value, onChange, placeholder }) => {
  const [focused, setFocused] = useState(false);
  const [show, setShow] = useState(false);
  const isPassword = type === "password";

  return (
    <div style={{ marginBottom: 18 }}>
      <label style={{ display: "block", fontSize: 13, fontWeight: 600, color: "#475569", marginBottom: 6 }}>
        {label}
      </label>
      <div style={{ position: "relative" }}>
        <input
          type={isPassword && show ? "text" : type}
          value={value}
          onChange={e => onChange(e.target.value)}
          placeholder={placeholder}
          onFocus={() => setFocused(true)}
          onBlur={() => setFocused(false)}
          style={{
            width: "100%",
            padding: "12px 16px",
            paddingRight: isPassword ? 44 : 16,
            borderRadius: 12,
            border: `2px solid ${focused ? "#1a5fd4" : "#e2e8f0"}`,
            background: focused ? "#fff" : "#f8fafc",
            fontSize: 15,
            color: "#0f172a",
            transition: "all .2s",
          }}
        />
        {isPassword && (
          <button
            onClick={() => setShow(!show)}
            style={{
              position: "absolute", right: 12, top: "50%",
              transform: "translateY(-50%)",
              background: "none", border: "none", cursor: "pointer",
              fontSize: 16, color: "#94a3b8",
            }}>
            {show ? "Hide" : "Show"}
          </button>
        )}
      </div>
    </div>
  );
};

export default function LoginPage({ onNavigate, onSuccess }) {
  const [mode, setMode] = useState("login");
  const [role, setRole] = useState("patient");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [nom, setNom] = useState("");
  const [prenom, setPrenom] = useState("");
  const [telephone, setTelephone] = useState("");
  const [regEmail, setRegEmail] = useState("");
  const [regPassword, setRegPassword] = useState("");

  const handleLogin = async () => {
    if (!email || !password) { setError("Veuillez remplir tous les champs."); return; }
    setLoading(true); setError("");
    try {
      const res = await fetch(`${API}/api/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, motDePasse: password }),
      });
      const data = await res.json();
      if (res.ok) {
        localStorage.setItem("token", data.token);
        localStorage.setItem("user", JSON.stringify(data));
        onSuccess?.(data);
      } else {
        setError(data.message || "Email ou mot de passe incorrect.");
      }
    } catch {
      setError("Impossible de contacter le serveur.");
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = async () => {
    if (!nom || !prenom || !regEmail || !regPassword) {
      setError("Veuillez remplir tous les champs."); return;
    }
    setLoading(true); setError(""); setSuccess("");
    try {
      const res = await fetch(`${API}/api/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          nom, prenom, email: regEmail,
          motDePasse: regPassword, telephone,
          role: role.toUpperCase(),
        }),
      });
      if (res.ok) {
        setSuccess("Compte cree avec succes ! Connectez-vous.");
        setMode("login");
      } else {
        const data = await res.json();
        setError(data.message || "Erreur lors de l inscription.");
      }
    } catch {
      setError("Impossible de contacter le serveur.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <style>{style}</style>
      <div style={{ minHeight: "100vh", display: "flex" }}>

        {/* Panneau gauche */}
        <div style={{
          flex: 1,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          background: "linear-gradient(145deg, #0d3d8f, #1a5fd4)",
          padding: 48,
          position: "relative",
          overflow: "hidden",
        }}>
          <div style={{
            position: "absolute", width: 300, height: 300,
            borderRadius: "50%", background: "white", opacity: 0.08,
            top: -80, left: -80,
          }} />
          <div style={{
            position: "absolute", width: 200, height: 200,
            borderRadius: "50%", background: "white", opacity: 0.08,
            bottom: -40, right: -60,
          }} />
          <div style={{ position: "relative", zIndex: 1, textAlign: "center", color: "white" }}>
            <div style={{ fontSize: 64, marginBottom: 24 }}>+</div>
            <h1 style={{ fontFamily: "serif", fontSize: 42, fontWeight: 900, marginBottom: 16 }}>
              Sante237
            </h1>
            <p style={{ fontSize: 16, opacity: 0.85, lineHeight: 1.7, maxWidth: 280 }}>
              Prenez rendez-vous avec les meilleurs medecins du Cameroun en quelques clics.
            </p>
            <div style={{ display: "flex", gap: 32, marginTop: 48, justifyContent: "center" }}>
              {[{ val: "500+", label: "Medecins" }, { val: "12k+", label: "Patients" }, { val: "98%", label: "Satisfaction" }].map(s => (
                <div key={s.label}>
                  <div style={{ fontSize: 26, fontWeight: 700 }}>{s.val}</div>
                  <div style={{ fontSize: 12, opacity: 0.7 }}>{s.label}</div>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Panneau droit */}
        <div style={{
          width: 480,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          padding: "48px 40px",
          background: "white",
          boxShadow: "-20px 0 60px rgba(26,95,212,0.08)",
        }}>
          <div className="fade1" style={{ marginBottom: 32, textAlign: "center" }}>
            <span style={{ fontSize: 28, fontWeight: 700, color: "#1a5fd4" }}>Sante237</span>
          </div>

          {/* Toggle */}
          <div className="fade1" style={{
            display: "flex", background: "#f1f5f9", borderRadius: 12,
            padding: 4, marginBottom: 32, width: "100%",
          }}>
            {["login", "register"].map(m => (
              <button key={m} onClick={() => { setMode(m); setError(""); setSuccess(""); }} style={{
                flex: 1, padding: "10px 0", borderRadius: 10, border: "none",
                background: mode === m ? "white" : "transparent",
                color: mode === m ? "#1a5fd4" : "#64748b",
                fontWeight: mode === m ? 700 : 500,
                fontSize: 14, cursor: "pointer",
                boxShadow: mode === m ? "0 2px 8px rgba(0,0,0,0.08)" : "none",
                transition: "all .2s",
              }}>
                {m === "login" ? "Connexion" : "Inscription"}
              </button>
            ))}
          </div>

          {/* Messages */}
          {error && (
            <div style={{
              width: "100%", padding: "12px 16px", borderRadius: 10,
              background: "#fee2e2", color: "#991b1b", fontSize: 13,
              marginBottom: 16, border: "1px solid #fecaca",
            }}>
              {error}
            </div>
          )}
          {success && (
            <div style={{
              width: "100%", padding: "12px 16px", borderRadius: 10,
              background: "#dcfce7", color: "#166534", fontSize: 13,
              marginBottom: 16, border: "1px solid #bbf7d0",
            }}>
              {success}
            </div>
          )}

          {/* Connexion */}
          {mode === "login" && (
            <div className="fade2" style={{ width: "100%" }}>
              <h2 style={{ fontSize: 26, fontWeight: 700, marginBottom: 6, color: "#0f172a" }}>
                Bon retour !
              </h2>
              <p style={{ color: "#64748b", fontSize: 14, marginBottom: 28 }}>
                Connectez-vous a votre compte Sante237
              </p>
              <InputField label="Email" type="email" value={email} onChange={setEmail} placeholder="votre@email.com" />
              <InputField label="Mot de passe" type="password" value={password} onChange={setPassword} placeholder="Min. 8 caracteres" />
              <button onClick={handleLogin} disabled={loading} style={{
                width: "100%", padding: "14px", borderRadius: 12, border: "none",
                background: loading ? "#94a3b8" : "linear-gradient(135deg, #1a5fd4, #0d3d8f)",
                color: "white", fontSize: 16, fontWeight: 700,
                cursor: loading ? "not-allowed" : "pointer",
                marginTop: 8,
              }}>
                {loading ? "Connexion..." : "Se connecter"}
              </button>
              <p style={{ textAlign: "center", marginTop: 16, fontSize: 13, color: "#64748b" }}>
                Pas de compte ?{" "}
                <span onClick={() => setMode("register")} style={{ color: "#1a5fd4", cursor: "pointer", fontWeight: 600 }}>
                  S inscrire
                </span>
              </p>
            </div>
          )}

          {/* Inscription */}
          {mode === "register" && (
            <div className="fade2" style={{ width: "100%" }}>
              <h2 style={{ fontSize: 26, fontWeight: 700, marginBottom: 6, color: "#0f172a" }}>
                Creer un compte
              </h2>
              <p style={{ color: "#64748b", fontSize: 14, marginBottom: 20 }}>
                Rejoignez Sante237 des aujourd hui
              </p>

              <div style={{ display: "flex", gap: 10, marginBottom: 20 }}>
                {["patient", "medecin"].map(r => (
                  <button key={r} onClick={() => setRole(r)} style={{
                    flex: 1, padding: "10px", borderRadius: 10,
                    border: `2px solid ${role === r ? "#1a5fd4" : "#e2e8f0"}`,
                    background: role === r ? "#dbeafe" : "white",
                    color: role === r ? "#1a5fd4" : "#64748b",
                    fontWeight: 600, fontSize: 14, cursor: "pointer",
                  }}>
                    {r === "patient" ? "Patient" : "Medecin"}
                  </button>
                ))}
              </div>

              <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 12 }}>
                <InputField label="Nom" value={nom} onChange={setNom} placeholder="Mbarga" />
                <InputField label="Prenom" value={prenom} onChange={setPrenom} placeholder="Jean" />
              </div>
              <InputField label="Telephone" value={telephone} onChange={setTelephone} placeholder="+237 6XX XXX XXX" />
              <InputField label="Email" type="email" value={regEmail} onChange={setRegEmail} placeholder="votre@email.com" />
              <InputField label="Mot de passe" type="password" value={regPassword} onChange={setRegPassword} placeholder="Min. 8 caracteres" />

              <button onClick={handleRegister} disabled={loading} style={{
                width: "100%", padding: "14px", borderRadius: 12, border: "none",
                background: loading ? "#94a3b8" : "linear-gradient(135deg, #1a5fd4, #0d3d8f)",
                color: "white", fontSize: 16, fontWeight: 700,
                cursor: loading ? "not-allowed" : "pointer",
                marginTop: 4,
              }}>
                {loading ? "Inscription..." : "Creer mon compte"}
              </button>

              <p style={{ textAlign: "center", marginTop: 16, fontSize: 13, color: "#64748b" }}>
                Deja un compte ?{" "}
                <span onClick={() => setMode("login")} style={{ color: "#1a5fd4", cursor: "pointer", fontWeight: 600 }}>
                  Se connecter
                </span>
              </p>
            </div>
          )}

          <p className="fade4" style={{ marginTop: 24, fontSize: 13, color: "#94a3b8", cursor: "pointer" }}
            onClick={() => onNavigate?.("home")}>
            Retour a l accueil
          </p>
        </div>
      </div>
    </>
  );
}
