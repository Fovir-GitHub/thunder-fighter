{
  description = "Devshell for Java.";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

  outputs = {
    self,
    nixpkgs,
  }: let
    system = "x86_64-linux";
    pkgs = import nixpkgs {inherit system;};

    jdkWithFX = pkgs.openjdk.override {
      enableJavaFX = true;
      openjfx_jdk = pkgs.openjfx.override {withWebKit = true;};
    };
  in {
    devShells.${system}.default = pkgs.mkShell {
      # Add packages here.
      buildInputs = [
        jdkWithFX
        pkgs.maven
        pkgs.just
      ];

      shellHook = ''
        echo "Entering the development environment!"
        java --version
      '';
    };
  };
}
